package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_messages.data.GameMode;

/**
 * Notifies the server that a player wants
 * to play and should be put in matchmaking.
 *<br/><br/>
 *
 * Message <code>[P] Play</code>.
 * Sent from Client in Main Menu and Game over screens,
 * when they chose to play a new game.
 *
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class PlayMessage {
    /** Unique id for the player. */
    public String playerId;
    /** Player chosen name. */
    public String playerName;
    /** The Game mode type. */
    public GameMode gameMode;
}
