package no.ntnu.tdt4240.g17.cool_game.network;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.MessageClassLister;
import no.ntnu.tdt4240.g17.common.network.game_messages.MatchmadeMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.PlayMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;

import java.io.IOException;
import java.util.List;

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
    public GameClient(final int tcpPort, ClientData clientData) {
        this.tcpPort = tcpPort;
        this.client = new Client();
        MessageClassLister.getMessageClasses().forEach(client.getKryo()::register);

        client.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object message) {
                clientData.recieve(message);
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
     * Stop the client.
     */
    public void stop() {
        client.stop();
    }

    public static void main(String[] args) {
        ClientData clientData = new ClientData();
        GameClient gameClient = new GameClient(5777, clientData);
//        gameClient.run();
    }
}