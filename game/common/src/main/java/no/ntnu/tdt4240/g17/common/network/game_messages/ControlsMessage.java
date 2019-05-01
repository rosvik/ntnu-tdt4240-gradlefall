package no.ntnu.tdt4240.g17.common.network.game_messages;

import lombok.ToString;

/**
 * Notify the server of user input.
 * <br/><br/>
 *
 * Message <code>[CT] Controls</code>.
 * Sent from Client in Game screen, when playing.
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
@ToString
public class ControlsMessage {
    /** Unique id for the player. */
    public String playerId;
    /** The speed of the player 0-100. */
    public float moveSpeed;
    /** In which angle the player wants to walk. */
    public float moveAngleRadians;
    /** If the player wants to jump? */
    public boolean jump;
    /** If the player wants to shoot? */
    public boolean shoot;
    /** In which angle the player wants to shoot. */
    public float shootAngleRadians;
    /** If the player wants to place block? */
    public boolean placeBlock;
    /** In which angle the player wants to place block. */
    public float placeBlockAngleRadians;
}
