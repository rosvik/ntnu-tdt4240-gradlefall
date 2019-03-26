package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_messages.data.Block;
import no.ntnu.tdt4240.g17.common.game_messages.data.Effect;
import no.ntnu.tdt4240.g17.common.game_messages.data.Powerup;
import no.ntnu.tdt4240.g17.common.game_messages.data.Projectile;
import no.ntnu.tdt4240.g17.common.game_messages.data.UpdateMessagePlayer;

import java.util.List;

/**
 * Created by Morten 'bujordet' Bujordet on 3/15/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class UpdateMessage {
    /** The information about the player state. */
    public List<UpdateMessagePlayer> updatePlayers;
    /** Sounds to trigger this game tick. */
    public Enum sound;
    /** List of all game powerups. */
    public List<Powerup> powerups;
    /** List of all game blocks. */
    public List<Block> blocks;
    /** List of all game projectiles. */
    public List<Projectile> projectiles;
    /** List of all game effects. */
    public List<Effect> effects;
}
