package no.ntnu.tdt4240.g17.common.network.game_messages.data;

/**
 * A game mode determines the rules for how a player
 * wins a game.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public enum GameMode {
    /** Survive the entire round. */
    LAST_MAN_STANDING,

    /** Gather the most kills. */
    HEADHUNTER
}
