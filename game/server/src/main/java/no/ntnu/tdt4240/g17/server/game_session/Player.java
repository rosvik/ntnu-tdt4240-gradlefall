package no.ntnu.tdt4240.g17.server.game_session;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

/**
 * A player in a game session.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class Player {
    /** Player id to separate players in a session. */
    @Getter
    private String id;

    @Getter @Setter
    private String playerName;

    @Getter
    private PlayerConnection playerConnection;

    /**
     * Create a new player.
     * @param playerId the session unique playerId.
     * @param playerConnection the connection for the player
     */
    public Player(@NotNull @NonNull final String playerId, final PlayerConnection playerConnection) {
        this.id = playerId;
        this.playerConnection = playerConnection;
    }


}
