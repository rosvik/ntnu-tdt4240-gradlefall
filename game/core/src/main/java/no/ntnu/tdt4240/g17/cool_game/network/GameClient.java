package no.ntnu.tdt4240.g17.cool_game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.MessageClassLister;

/**
 * Handles connections and communication with servers.
 * @author Morten 'bujordet' Bujordet
 */
@Slf4j
public class GameClient {
    private final int tcpPort;
    private final Client client;
    private String serverIp;

    /**
     * @param serverIp the server IP address
     * @param tcpPort TCP Port to listen to.
     * @param clientData The data class to save information to.
     * Create new game client.
     */
    public GameClient(final String serverIp, final int tcpPort, final ClientData clientData) {
        this.serverIp = serverIp;
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
     * This call is blocking, so the method will not return until a connection is made,
     * or it gives up or errs.
     * @throws IOException when too many errors happened.
     */
    public final void connectBlocking() throws IOException {
        client.start();
        boolean failed = true;
        long waitTime = 400;
        long maxWait = 15000; // 15 seconds. roughly 6 failures in a row.
        while (failed) {
            try {
                client.connect(5000, serverIp, tcpPort);
                failed = false;
            } catch (IOException e) {
                log.error("Unable to connect to {}", tcpPort, e);
                try {
                    Thread.sleep(waitTime);
                    waitTime *= 1.8f;
                    if (waitTime > maxWait) {
                        throw new IOException("Unable to connect after too many retries", e);
                    }
                } catch (InterruptedException ex) {
                    log.error("Thread {} waiting", ex);
                }
            }
        }
        client.start(); // Starts a new thread
    }

    /**
     * Send a message to the server.
     *
     * @param message The message object.
     */
    public void send(final Object message) {
        client.sendTCP(message);
    }

    /**
     * Stop the client.
     */
    public void stop() {
        client.stop();
    }
}
