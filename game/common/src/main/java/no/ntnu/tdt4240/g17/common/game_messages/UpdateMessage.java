package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_helpers.Block;
import no.ntnu.tdt4240.g17.common.game_helpers.Effect;
import no.ntnu.tdt4240.g17.common.game_helpers.Powerup;
import no.ntnu.tdt4240.g17.common.game_helpers.Projectile;
import no.ntnu.tdt4240.g17.common.game_helpers.UpdateMessagePlayer;

import java.util.List;

/**
 * Created by Morten 'bujordet' Bujordet on 3/15/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("CheckStyle")
public class UpdateMessage {
    /** The information about the player state */
    public List<UpdateMessagePlayer> updatePlayers;
    /** Sounds to trigger this game tick. */
    public Enum sound;
    /** List of all effects, projectiles, blocks and powerups */
    public List<Effect> effects;
    public List<Projectile> projectiles;
    public List<Block> blocks;
    public List<Powerup> powerups;
}
