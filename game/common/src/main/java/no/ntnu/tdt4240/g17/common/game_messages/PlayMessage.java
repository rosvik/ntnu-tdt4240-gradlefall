package no.ntnu.tdt4240.g17.common.game_messages;

/**
 * Created by Morten 'bujordet' Bujordet on 3/15/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("CheckStyle")
public class PlayMessage {
    /** Unique id for the player. */
    public String playerId;
    /** Player chosen name. */
    public String playerName;
    /** The Game mode type. */
    public Enum gameMode;
}
