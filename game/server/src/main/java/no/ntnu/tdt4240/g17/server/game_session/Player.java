package no.ntnu.tdt4240.g17.server.game_session;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.NonNull;

/**
 * A player in a game session.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class Player {
    /** Player id to separate players in a session. */
    @Getter
    private String id;

    /**
     * Create a new player.
     * @param playerId the session unique playerId.
     */
    public Player(@NotNull @NonNull final String playerId) {
        this.id = playerId;
    }


}
