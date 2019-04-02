package no.ntnu.tdt4240.g17.server.network;

/**
 * The state of a connected player.
 * This is used for the server connection to keep track
 * of where the user is.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public enum PlayerState {
    /**
     * We lost track, desynced.
     */
    UNKNOWN,

    /**
     * The player is not yet assigned an ID.
     */
    UNIDENTIFIED,


    /** */
    NOT_PLAYING,

    /** */
    IN_MATCHMAKING,

    /** */
    IN_LOBBY,

    /** */
    IN_GAME
}
