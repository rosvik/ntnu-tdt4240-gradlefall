package no.ntnu.tdt4240.g17.server;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.network.GameServer;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/30/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
class ShutdownProcedureThread extends Thread {
    private final GameServer gameServer;
    private final Thread serverThread;

    /**
     * Create a new thread for shutdown.
     * Remember to call {@link #installAsShutdownHook()}.
     *
     * @param gameServer   the game server to also shut down
     * @param serverThread the server thread to wait for when shutting down
     */
    ShutdownProcedureThread(final GameServer gameServer, final Thread serverThread) {
        this.gameServer = gameServer;
        this.serverThread = serverThread;
    }

    /**
     * Install the thread as a shutdown hook.
     */
    public void installAsShutdownHook() {
        Runtime.getRuntime().addShutdownHook(this);
    }

    @Override
    public void run() {
        setName("ShutdownHook");
        log.info("Shutting down game server.");
        gameServer.stop();
        try {
            log.info("Waiting for server thread...");
            serverThread.join();
        } catch (InterruptedException ignored) {
        }
        log.info("Shut down.");
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
    }
}
