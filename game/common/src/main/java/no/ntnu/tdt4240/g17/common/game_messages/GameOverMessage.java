package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_helpers.GameOverMessagePlayer;

import java.util.List;

/**
 * Created by Morten 'bujordet' Bujordet on 3/15/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class GameOverMessage {
    /** The info for each player in the game, containing: playerId, playerName, playerSkin, playerScore. */
    public List<GameOverMessagePlayer> gamePlayers;
    /** The player ids for the winners of the game. */
    public List<String> winnerId;
    /** The current game mode. */
    public Enum gameMode;
}
