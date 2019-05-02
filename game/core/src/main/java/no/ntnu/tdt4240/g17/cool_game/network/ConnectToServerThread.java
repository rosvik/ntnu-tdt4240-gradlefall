package no.ntnu.tdt4240.g17.cool_game.network;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

/**
 * Connects the singleton in {@link GameClient#getNetworkClientInstance()} to the server.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class ConnectToServerThread extends Thread {

    /** Creates a new thread instance. */
    public ConnectToServerThread() {
        super("Connection thread");
        setDaemon(true);
    }

    @Override
    public void run() {
        final Client networkClient = GameClient.getNetworkClientInstance();
        try {
            synchronized (GameClient.class) {
                if (GameClient.getNetworkClientInstance().isConnected()) {
                    return;
                }
                GameClient.connectClientBlocking(networkClient,
                        NetworkSettings.getServerAddress(), NetworkSettings.getPort());
            }
        } catch (IOException e) {
        }
    }
}
