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
    private final MessageHandlerDelegator handlerDelegator;

    /**
     * Create a new game server.
     *  @param tcpPort         TCP port to listen to.
     * @param failureListener a listener to report failures to.
     * @param handlerDelegator a delegator that handles messages
     */
    public GameServer(final int tcpPort, final FailureListener failureListener,
                      final MessageHandlerDelegator handlerDelegator) {
        this.tcpPort = tcpPort;
        this.failureListener = failureListener;
        this.handlerDelegator = handlerDelegator;
        server = new Server() {
            @Override
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };
    }

    @Override
    public final void run() {
        ServerMinLogBridge.bridgeMinlogToSlf4j();

        // the listener will modify #connections and also synchronize on it.
        server.addListener(new PlayerConnectionListener(connections, handlerDelegator));

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

        server.run(); // blocks until stop==true!
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
