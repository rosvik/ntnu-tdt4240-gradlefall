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

    /** The ip of the server to connect to. */
    @Getter @Setter
    private static String serverIp = "localhost";

    /** The port of the server to connect to. */
    @Getter @Setter
    private static int port = 5777;
}
