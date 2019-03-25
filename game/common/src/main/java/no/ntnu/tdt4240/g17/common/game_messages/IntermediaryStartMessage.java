package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_helpers.IntermediaryStartMessagePlayer;

import java.util.List;

/**
 * Created by Morten 'bujordet' Bujordet on 3/25/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class IntermediaryStartMessage {
    /** List of players containing playerName, playerId, position and score. */
    public List<IntermediaryStartMessagePlayer> players;
    /** The number of the round that just ended and is summarized. */
    public Integer roundNumber;
    /** The arena description for the next round, in case asset loading is needed. */
    public Enum nextArena;
    /** The game mode for the next round. */
    public Enum gameMode;
}
