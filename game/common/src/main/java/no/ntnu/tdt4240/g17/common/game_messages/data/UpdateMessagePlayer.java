package no.ntnu.tdt4240.g17.common.game_messages.data;


import java.util.List;

/**
 * Created by Morten 'bujordet' Bujordet on 3/15/2019.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class UpdateMessagePlayer extends Player {
    /** Aiming direction. */
    public float aimingAngle;
    /** The projectile ammunition count of the Player. */
    public int projectileAmmoCount;
    /** The block ammunition count of the Player. */
    public int blockAmmoCount;
    /** The aliveness of the Player. */
    public boolean isAlive;
    /** All active powerups for all player ids: String: playerId, List: List of powerups. */
    public List<Powerup> activePowerups;
}
