package no.ntnu.tdt4240.g17.cool_game.network;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;

import lombok.extern.slf4j.Slf4j;

/**
 * Discovers any local servers, and connects to them if possible.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class DiscoverAndConnectToLanServerThread extends Thread {

    /** Create a new thread instance. */
    public DiscoverAndConnectToLanServerThread() {
        super("LocalServer Connection thread");
        setDaemon(true);
    }

    @Override
    public void run() {
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
    }
}
