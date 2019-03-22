package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.availability.FailureListener;

/**
 * Handles connections and communication with clients.
 *
 * Start it with {@link #run()} in a {@link Thread}.
 * Stop it with {@link #stop()}
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class GameServer implements Runnable {
    private final int tcpPort;
    private final FailureListener failureListener;

    private final List<PlayerConnection> connections = new ArrayList<>();
    private final Server server;

    /**
     * Create a new game server.
     *
     * @param tcpPort         TCP port to listen to.
     * @param failureListener a listener to report failures to.
     */
    public GameServer(final int tcpPort, final FailureListener failureListener) {
        this.tcpPort = tcpPort;
        this.failureListener = failureListener;
        server = new Server() {
            @Override
            protected Connection newConnection() {
                return super.newConnection();
            }
        };
    }

    @Override
    public final void run() {
        ServerMinLogBridge.bridgeMinlogToSlf4j();

        server.addListener(new PlayerConnectionListener() {
            @Override
            public void connected(final PlayerConnection connection) {
                // Runs on same thread as Server#update
                synchronized (connections) {
                    connections.add(connection);
                }
                log.debug("Client {} connected. Network RTT: {}", connection.getID(), connection.getReturnTripTime());
            }

            @Override
            public void received(final PlayerConnection connection, final Object object) {
                // Runs on same thread as Server#update
                // TODO: 3/22/2019 Implement this
                /*
                Should probably do it like this:
                find if the player is in a game.
                    if so, find the thread that handles the game engine.
                    Send the message to that thread.
                if not in a game:
                    send to a generic handler/executor for inactive clients
                if entering matchmaking:
                    send to matchmaking thread
                 */
            }

            @Override
            public void idle(final PlayerConnection connection) {
            }

            @Override
            public void disconnected(final PlayerConnection connection) {
                // unspecified thread
                // FIXME: 3/22/2019 only remove from connections or similiar if the user intended to disconnect?
                // Do connection interrupts come here?
                synchronized (connections) {
                    connections.remove(connection);
                }
                log.debug("Client {} disconnected", connection.getID());
            }
        });

        try {
            server.bind(tcpPort);
        } catch (IOException ex) {
            log.error("Unable to bind to {}", tcpPort, ex);
            // FIXME: 3/22/2019 Retry after some time?
            // No option for graceful degradation here.
            // TODO: 3/22/2019 Notify some service that we failed?
            final RuntimeException exception = new RuntimeException("Unable to bind to port " + tcpPort, ex);
            failureListener.reportFailure(FailureListener.Severity.FATAL, exception);
            System.exit(1);
        }

        server.run(); // blocks untill stop==true!
    }

    /**
     * Stop the server.
     */
    public void stop() {
        log.info("Stopping server");
        synchronized (this) {
            server.stop();
        }
    }
}
