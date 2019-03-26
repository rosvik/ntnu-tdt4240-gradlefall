package no.ntnu.tdt4240.g17.common.network.game_messages;

/**
 * Notify the server that the user should be
 * removed from matchmaking.
 * <br/><br/>
 *
 * Message <code>[C] Cancel</code>.
 * Sent from Client when in matchmaking queue screen.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class CancelMessage {
    /** Unique id for the player. */
    public String playerId;
}
