package no.ntnu.tdt4240.g17.common.game_messages;

import java.util.List;

import no.ntnu.tdt4240.g17.common.game_messages.data.Block;
import no.ntnu.tdt4240.g17.common.game_messages.data.Effect;
import no.ntnu.tdt4240.g17.common.game_messages.data.Powerup;
import no.ntnu.tdt4240.g17.common.game_messages.data.Projectile;
import no.ntnu.tdt4240.g17.common.game_messages.data.SoundEffect;
import no.ntnu.tdt4240.g17.common.game_messages.data.UpdateMessagePlayer;

/**
 * <p>This synchronizes game state from server to client.
 * The most important message.
 * Tells the client where everything is positioned
 * and what sounds to play etc.
 * </p>
 * <br/><br/>
 *
 * Message <code>[U] Update</code>.
 * Sent from Server in game screen, when Client is playing.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class UpdateMessage {
    /** The information about the player state. */
    public List<UpdateMessagePlayer> players;
    /** Sounds to trigger this game tick. */
    public List<SoundEffect> sounds;
    /** List of all game powerups. */
    public List<Powerup> powerups;
    /** List of all game blocks. */
    public List<Block> blocks;
    /** List of all game projectiles. */
    public List<Projectile> projectiles;
    /** List of all game effects. */
    public List<Effect> effects;
}
