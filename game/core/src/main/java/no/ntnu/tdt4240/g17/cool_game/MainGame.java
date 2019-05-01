package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.network.NetworkSettings;
import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * Main game class.
 */
@Slf4j
public class MainGame extends ApplicationAdapter {

    private Navigator navigator;
    @Override
    public final void create() {
        navigator = new Navigator();

        final Thread connectionThread = new Thread(() -> {
            final Client networkClient = GameClient.getNetworkClientInstance();
            try {
                synchronized (GameClient.class) {
                    if (GameClient.getNetworkClientInstance().isConnected()) {
                        return;
                    }
                    GameClient.connectClientBlocking(networkClient,
                            NetworkSettings.getServerIp(), NetworkSettings.getPort());
                }
            } catch (IOException e) {
            }
        }, "Connection thread");
        connectionThread.setDaemon(true);
        connectionThread.start();

        final Thread localServerDiscovery = new Thread(() -> {
            final Client localServerClient = new Client();
            final InetAddress inetAddress = localServerClient
                    .discoverHost(NetworkSettings.getUdpPort(), 6000);

            if (inetAddress != null) {
                final String hostAddress = inetAddress.getHostAddress();
                log.info("Discovered local server: {}", hostAddress);
                try {
                    GameClient.connectClientBlocking(localServerClient, hostAddress, NetworkSettings.getPort());
                    if (localServerClient.isConnected()) {
                        localServerClient.close();
                        final Client networkClientInstance = GameClient.getNetworkClientInstance();
                        GameClient.connectClientBlocking(networkClientInstance,
                                hostAddress, NetworkSettings.getPort());
                        NetworkSettings.setServerIp(hostAddress);
                        GameClient.setIsLocalNetwork(true);
                        log.info("Chose local server '{}' instead.", hostAddress);
                    }
                } catch (IOException ignored) {
                }
            } else {
                log.debug("No local servers found.");
            }
        }, "LocalServer Connection thread");
        localServerDiscovery.setDaemon(true);
        localServerDiscovery.start();
    }

    @Override
    public final void render() {
        navigator.getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public final void dispose() {
        if (navigator != null) {
            navigator.dispose();
        }
    }
}
