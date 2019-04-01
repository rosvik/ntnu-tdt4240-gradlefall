package no.ntnu.tdt4240.g17.cool_game.network;


import com.esotericsoftware.kryonet.Client;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.MessageClassLister;

import java.io.IOException;

/**
 * Handles connections and communication with servers.
 * @author Morten 'bujordet' Bujordet
 */
@Slf4j
public class GameClient {
    private final int tcpPort;
    private final Client client;

    /**
     * @param tcpPort TCP Port to listen to.
     * Create new game client.
     */
    public GameClient(final int tcpPort) {
        this.tcpPort = tcpPort;
        this.client = new Client();
        MessageClassLister.getMessageClasses().forEach(client.getKryo()::register);
        client.start();
    }

    /**
     * Start the server.
     */
    public final void run() {
        boolean failed = true;
        long waitTime = 400;
        while (failed) {
            try {
                client.connect(5000, "localhost", tcpPort);
                failed = false;
            } catch (IOException e) {
                log.error("Unable to connect to {}", tcpPort, e);
                try {
                    Thread.sleep(waitTime);
                    waitTime *= 1.2;
                } catch (InterruptedException ex) {
                    log.error("Thread {} waiting", ex);
                }
            }
        }
        client.run();
    }

    /**
     * Stop the client.
     */
    public void stop() {
        client.stop();
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient(5777);
        gameClient.run();
    }
}
