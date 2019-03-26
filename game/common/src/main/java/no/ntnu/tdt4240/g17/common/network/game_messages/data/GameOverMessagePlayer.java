package no.ntnu.tdt4240.g17.common.network.game_messages.data;

/**
 * Created by Morten 'bujordet' Bujordet on 3/25/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class GameOverMessagePlayer {
    /** Unique id for the player. */
    public String playerId;
    /** Player chosen name. */
    public String playerName;
    /** The description for each player id's visual appearance. */
    public PlayerSkin playerSkin;
    /** The score for each player id; String: playerId, Integer: score. */
    public int playerScore;
}
