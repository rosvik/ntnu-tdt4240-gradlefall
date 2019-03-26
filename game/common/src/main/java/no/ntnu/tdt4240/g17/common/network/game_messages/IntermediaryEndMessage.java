package no.ntnu.tdt4240.g17.common.network.game_messages;

import java.util.List;

import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Player;

/**
 * Lets the server tell clients to start the next round.
 *<br/><br/>
 *
 * Message <code>[IE] Intermediary end</code>.
 * Sent from Server to trigger a <code>next round</code> event.
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class IntermediaryEndMessage {
    /** List of players containing playerName, playerId and position. */
    public List<Player> players;
    /** The arena description for the next round, in case asset loading is needed. */
    public Arena nextArena;
    /** The game mode for the next round. */
    public GameMode gameMode;

}
