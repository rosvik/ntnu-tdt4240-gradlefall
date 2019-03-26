package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.common.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.common.game_messages.data.IntermediaryStartMessagePlayer;

import java.util.List;

/**
 * <p>Used by the server to tell the client to
 * show a screen with scores after this round.
 * Can also let the client prepare for the next round.
 * </p>
 * <br/><br/>
 *
 * Message <code>[IS] Intermediary start</code>.
 * Sent from Server to trigger a <code>round over</code> event.
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class IntermediaryStartMessage {
    /** List of players containing playerName, playerId, position and score. */
    public List<IntermediaryStartMessagePlayer> players;
    /** The number of the round that just ended and is summarized. */
    public int roundNumber;
    /** The arena description for the next round, in case asset loading is needed. */
    public Arena nextArena;
    /** The game mode for the next round. */
    public GameMode gameMode;
}
