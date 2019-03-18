package no.ntnu.tdt4240.g17.server.game_session;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/11/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class Player {
    /** Player id to separate players in a session. */
    private int id;

    /**
     * Create a new player.
     * @param playerId the session unique playerId.
     */
    public Player(final int playerId) {
        this.id = playerId;
    }

    /**
     * @return the player id.
     */
    public int getId() {
        return id;
    }
}
