package no.ntnu.tdt4240.g17.cool_game.network;

import lombok.Getter;
import lombok.Setter;

/**
 * Utility class for networking.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class NetworkSettings {
    /** Utility class. */
    private NetworkSettings() { }

    /** The ip of the server to connectBlocking to. */
    @Getter @Setter
    private static String serverIp = "10.22.212.196"; //"localhost";

    /** The port of the server to connectBlocking to. */
    @Getter @Setter
    private static int port = 5777;

    /** The port that local network servers will broadcast themselves on. */
    @Getter
    private static int udpPort = 5778;
}
