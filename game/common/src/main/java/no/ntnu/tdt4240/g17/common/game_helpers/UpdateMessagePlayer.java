package no.ntnu.tdt4240.g17.common.game_helpers;


import java.util.List;

@SuppressWarnings("CheckStyle")
public class UpdateMessagePlayer extends Player {
    /** Aiming direction. */
    public Float aimingAngle;
    /** The projectile ammunition count of the Player. */
    public Integer projectileAmmoCount;
    /** The block ammunition count of the Player. */
    public Integer blockAmmoCount;
    /** The aliveness of the Player. */
    public Boolean aliveState;
    /** All active powerups for all player ids: String: playerId, List: List of powerups */
    public List<Powerup> activePowerups;
}
