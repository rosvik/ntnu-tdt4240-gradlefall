package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_messages.data.MatchmadeMessagePlayer;
import java.util.List;

/**
 * Created by Morten 'bujordet' Bujordet on 3/15/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class MatchmadeMessage {
    /** List of players containing playerName, playerId, position and playerSkin. */
    public List<MatchmadeMessagePlayer> players;
    /** The Game mode type. */
    public Enum gameMode;
    /** The arena type. */
    public Enum arena;
}
