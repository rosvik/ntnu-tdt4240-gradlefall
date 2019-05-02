package no.ntnu.tdt4240.g17.cool_game.network;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for networking.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class NetworkSettings {

    private static final String DEFAULT_SERVER_HOST = "localhost";

    /** Utility class. */
    private NetworkSettings() { }

    /** The ip or hostname of the server to connectBlocking to. */
    @Getter @Setter
    private static String serverAddress =  getDefaultHostname();

    /** The port of the server to connectBlocking to. */
    @Getter @Setter
    private static int port = 5777;

    /** The port that local network servers will broadcast themselves on. */
    @Getter
    private static int udpPort = 5778;

    /** Read a ip address from environment variables or default to {@value DEFAULT_SERVER_HOST}.
     * @return the host addresss to use.
     */
    private static String getDefaultHostname() {
        final String environmentIp = System.getenv("server.ip");
        if (environmentIp != null && !environmentIp.trim().isEmpty()) {
            log.info("Using ip address {}", environmentIp);
            return environmentIp.trim();
        }
        return DEFAULT_SERVER_HOST;
    }
}
