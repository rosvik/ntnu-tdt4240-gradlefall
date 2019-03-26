package no.ntnu.tdt4240.g17.common.game_messages.data;

/**
 * Created by Morten 'bujordet' Bujordet on 3/25/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class Block {
    /** Id of the block. */
    public String blockId;
    /** Type of block. */
    public Enum blockType;
    /** Position of the block. */
    public Position blockPosition;
}
