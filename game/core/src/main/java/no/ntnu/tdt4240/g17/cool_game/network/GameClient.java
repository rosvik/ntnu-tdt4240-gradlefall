package no.ntnu.tdt4240.g17.cool_game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

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
     * @param clientData The data class to save information to.
     * Create new game client.
     */
    public GameClient(final int tcpPort, final ClientData clientData) {
        this.tcpPort = tcpPort;
        this.client = new Client();
        MessageClassLister.getMessageClasses().forEach(client.getKryo()::register);

        client.addListener(new Listener() {
            @Override
            public void received(final Connection connection, final Object message) {
                clientData.receive(message);
            }
        });
    }

    /**
     * Start the client.
     */
    public final void run() {
        client.start();
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
     * Send a message to the server.
     *
     * @param message The message object.
     */
    void send(Object message) {
        client.sendTCP(message);
    }

    /**
     * Stop the client.
     */
    public void stop() {
        client.stop();
    }
}
