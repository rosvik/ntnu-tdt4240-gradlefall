package no.ntnu.tdt4240.g17.cool_game.network;


import com.esotericsoftware.kryonet.Client;
import lombok.extern.slf4j.Slf4j;

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
        this.tcpPort = 5777;
        this.client = new Client();
    }

    /**
     * Start the server.
     */
    public final void run() {
        client.start();
        try {
            client.connect(3000, "localhost", tcpPort);
        } catch (IOException e) {
            log.error("Unable to connect to {}", tcpPort, e);
            // FIXME: 3/22/2019 Retry after some time?
            // No option for graceful degradation here.
            // TODO: 3/22/2019 Notify some service that we failed?
            final RuntimeException exception = new RuntimeException("Unable to bind to port " + tcpPort, e);
            System.exit(1);
        }
        client.sendTCP("PING");
        client.run();
    }

    /**
     * Stop the client.
     */
    public void stop() {
        client.stop();
    }
}
