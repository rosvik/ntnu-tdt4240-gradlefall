package no.ntnu.tdt4240.g17.cool_game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.MessageClassLister;

/**
 * Handles connections and communication with servers.
 * @author Morten 'bujordet' Bujordet
 */
@Slf4j
public final class GameClient {
    private final int tcpPort;
    @Getter
    private final Client client;
    private String serverIp;

    @Nullable
    private static Client clientSingleton = null;
    /** @return A client singleton. It may or may not be connected to the server.
     * @see #connectClientBlocking(Client, String, int) */
    public static Client getNetworkClientInstance() {
        if (clientSingleton == null) {
            synchronized (GameClient.class) {
                if (clientSingleton == null) {
                    clientSingleton = new Client();
                    MessageClassLister.getMessageClasses().forEach(clientSingleton.getKryo()::register);
                }
            }
        }
        return clientSingleton;
    }

    /**
     * @param serverIp the server IP address
     * @param tcpPort TCP Port to listen to.
     * @param clientData The data class to save information to.
     * Create new game client.
     */
    public GameClient(final String serverIp, final int tcpPort, final ClientData clientData) {
        this.serverIp = serverIp;
        this.tcpPort = tcpPort;
        this.client = getNetworkClientInstance();
        client.addListener(new Listener() {
            @Override
            public void received(final Connection connection, final Object message) {
                clientData.receive(message);
            }
        });
    }

    /**
     * Ensure the client is connected.
     * @throws IOException if connecting fails
     */
    public void connectBlocking() throws IOException {
        if (!client.isConnected()) {
            connectClientBlocking(client, serverIp, tcpPort);
        }
    }

    /**
     * Start the client.
     * This call is blocking, so the method will not return until a connection is made,
     * or it gives up or errs.
     * @param serverIp the server ip to connect to
     * @param tcpPort the port to connect to
     * @param client the client to connect
     * @throws IOException when too many errors happened.
     */
    public static synchronized void connectClientBlocking(final Client client,
                                                          final String serverIp, final int tcpPort) throws IOException {
        boolean failed = true;
        long waitTimeMs = 400;
        long maxWaitMs = 15000; // 15 seconds. roughly 6 failures in a row.
        client.start(); // Starts a new thread. Must run while connect is tried.
        while (failed) {
            try {
                client.connect(5000, serverIp, tcpPort);
                failed = false;
            } catch (IOException e) {
                log.error("Unable to connectBlocking to {}", tcpPort, e);
                try {
                    Thread.sleep(waitTimeMs);
                    waitTimeMs *= 1.8f; //Retry with exponential backoff
                    if (waitTimeMs > maxWaitMs) {
                        client.stop();
                        throw new IOException("Unable to connectBlocking after too many retries", e);
                    }
                } catch (InterruptedException ex) {
                    log.error("Thread {} waiting", ex);
                }
            }
        }
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
