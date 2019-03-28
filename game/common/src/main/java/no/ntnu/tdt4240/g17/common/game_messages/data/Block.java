package no.ntnu.tdt4240.g17.common.game_messages.data;

/**
 * A placed block that can take 1 projectile hit.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class Block {
    /** Id of the block. */
    public String blockId;
    /** Id of the player that owns this block. */
    public String blockOwnerPlayerId;
    /** Position of the block. */
    public Position blockPosition;
}
