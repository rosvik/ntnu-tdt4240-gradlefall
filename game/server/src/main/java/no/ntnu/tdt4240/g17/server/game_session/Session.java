package no.ntnu.tdt4240.g17.server.game_session;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/25/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class Session {

    @Getter
    private List<PlayerConnection> connections = new ArrayList<>();
    @Getter
    private List<Player> players = new ArrayList<>();


    /** Create a new session. */
    public Session() {
    }



    /**
     * Send a message to the player.
     * @param player the receiver
     * @param message the message object
     */
    public void sendMessage(final Player player, final Object message) {
        // FIXME: 3/25/2019 send the message on a dedicated thread; it blocks
        for (PlayerConnection connection : connections) {
            if (player.getId().equals(connection.getId())) {
                connection.sendTCP(message);
                break;
            }
        }
    }
}
