package no.ntnu.tdt4240.g17.server.network;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.MessageClassLister;
import no.ntnu.tdt4240.g17.server.availability.FailureListener;

/**
 * Handles connections and communication with clients.
 *
 * Start it with {@link #run()} in a {@link Thread}.
 * Stop it with {@link #stop()}
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class GameServer implements Runnable, Disposable {
    private final int tcpPort;
    private int udpPort;
    private final FailureListener failureListener;

    private final List<PlayerConnection> connections = new ArrayList<>();
    private final Server server;
    private final MessageHandlerDelegator handlerDelegator;

    /**
     * Create a new game server.
     * @param tcpPort         TCP port to listen to.
     * @param failureListener a listener to report failures to.
     * @param handlerDelegator a delegator that handles messages
     * @param udpPort UDP port to listen to
     */
    public GameServer(final int tcpPort, final FailureListener failureListener,
                      final MessageHandlerDelegator handlerDelegator, final int udpPort) {
        this.tcpPort = tcpPort;
        this.failureListener = failureListener;
        this.handlerDelegator = handlerDelegator;
        this.udpPort = udpPort;

        server = new Server() {
            @Override
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };

        final Kryo kryo = server.getKryo();
        final List<Class> messageClasses = MessageClassLister.getMessageClasses();
        messageClasses.forEach(kryo::register);
        log.debug("Registered {} classes in kryo", messageClasses.size());
    }

    @Override
    public final void run() {
        ServerMinLogBridge.bridgeMinlogToSlf4j();

        // the listener will modify #connections and also synchronize on it.
        server.addListener(new PlayerConnectionListener(connections, handlerDelegator));

        try {
            server.bind(tcpPort, udpPort);
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

    /** Disposes server resoures. */
    @Override
    public void dispose() {
        try {
            server.dispose();
        } catch (IOException ignored) { }
    }
}
