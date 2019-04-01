package no.ntnu.tdt4240.g17.common.network.game_messages;

import java.util.List;

import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.MatchmadeMessagePlayer;

/**
 * Lets the server tell the client that a group of
 * players has been found, and the game will start.
 *<br/><br/>
 *
 * Message <code>[M] Matchmade</code>.
 * Sent from Server when Client is in loading state,
 * to trigger a <code>matchmade</code> event.
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class MatchmadeMessage {
    /** List of players containing playerName, playerId, position and playerSkin. */
    public List<MatchmadeMessagePlayer> players;
    /** The Game mode type. */
    public GameMode gameMode;
    /** The arena type. */
    public Arena arena;
}
