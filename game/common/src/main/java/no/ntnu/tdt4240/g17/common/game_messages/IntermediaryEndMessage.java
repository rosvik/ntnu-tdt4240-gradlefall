package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_helpers.Player;

import java.util.List;

/**
 * Created by Morten 'bujordet' Bujordet on 3/25/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class IntermediaryEndMessage {
    /** List of players containing playerName, playerId and position. */
    public List<Player> players;
    /** The arena description for the next round, in case asset loading is needed. */
    public Enum nextArena;
    /** The game mode for the next round. */
    public Enum gameMode;

}
