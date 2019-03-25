package no.ntnu.tdt4240.g17.server;

import com.badlogic.gdx.physics.box2d.Box2D;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.availability.FailureListener;
import no.ntnu.tdt4240.g17.server.network.GameServer;
import no.ntnu.tdt4240.g17.server.network.MessageHandlerDelegator;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;
import no.ntnu.tdt4240.g17.server.network.messageHandler.MessageHandler;

/**
 * Main class for the server.
 */
@Slf4j
public final class ServerMain {
    /** Hidden constructor for utility classes. */
    private ServerMain() { }

    /**
     * Starts the server.
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        log.info("Server starting up");
        log.trace("Loading box2d native libraries...");
        Box2D.init();
        log.trace("Loaded box2d native libraries.");

        // TODO: read server parameters from .properties file or environment.
        // eg. bort number and bind adress (127.0.0.1 or 0.0.0.0).
        // TODO: Start server to listen for incoming clients.
        // TODO: Register for heartbeats/ping?
        final FailureListener failureListener = new FailureListener() {
            @Override
            public void reportFailure(final Severity severity, final Throwable exception) {
                // FIXME make proper error handling.
                log.error("OOPS, FAILURE! FIXME: implement failure handling. Severity {}", severity.name(), exception);
            }
        };

        // TODO: 3/22/2019 Read from a config file or environment
        final int tcpPort = 5777;
        final MessageHandlerDelegator handlerDelegator = new MessageHandlerDelegator();
        final GameServer gameServer = new GameServer(tcpPort, failureListener, handlerDelegator);

        final ThreadGroup connectionThreadGroup = new ThreadGroup("Connection");
        final Thread serverThread = new Thread(connectionThreadGroup, gameServer, "GameServer");

        handlerDelegator.registerHandler((connection, message) -> log.info("Got message: {}", message), String.class);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                setName("ShutdownHook");
                log.info("Shutting down.");
                gameServer.stop();
                try {
                    serverThread.join();
                } catch (InterruptedException ignored) { }
                log.info("Shut down.");
                LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
                loggerContext.stop();
            }
        });

        log.info("Starting server");
        serverThread.start();

        log.info("Main thread exiting.");
    }
}
