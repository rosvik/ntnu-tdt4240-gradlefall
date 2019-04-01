package no.ntnu.tdt4240.g17.common.network.game_messages.data;

/**
 * A fired projectile.
 *
 * @author Morten 'bujordet' Bujordet
 */
@SuppressWarnings("VisibilityModifier")
public class Projectile {
    /** Id of the projectile. */
    public String projectileId;
    /** Position of the projectile. */
    public Position projectilePosition;
    /** Type of projectile. */
    public ProjectileType projectileType;
    /** Angle of the projectile. */
    public float projectileAngle;
}
