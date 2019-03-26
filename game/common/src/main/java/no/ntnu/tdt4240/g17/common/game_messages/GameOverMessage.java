package no.ntnu.tdt4240.g17.common.game_messages;

import java.util.List;

import no.ntnu.tdt4240.g17.common.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.common.game_messages.data.GameOverMessagePlayer;

/**
 * Tell the client that the game has ended
 * and has a winner.
 *<br/><br/>
 *
 * Message <code>[GO] Game over</code>.
 * Sent from Server to trigger a <code>game over</code> event.
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class GameOverMessage {
    /** The info for each player in the game, containing: playerId, playerName, playerSkin, playerScore. */
    public List<GameOverMessagePlayer> gamePlayers;
    /** The player ids for the winners of the game. */
    public List<String> winnerIds;
    /** The current game mode. */
    public GameMode gameMode;
}
