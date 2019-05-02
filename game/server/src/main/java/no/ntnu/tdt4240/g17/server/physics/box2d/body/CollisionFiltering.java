package no.ntnu.tdt4240.g17.server.physics.box2d.body;

/**
 * Holds filter for Box2D FixtureDef {@link com.badlogic.gdx.physics.box2d.FixtureDef#filter}.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class CollisionFiltering {
    /** Filter category for players. */
    public static final short CATEGORY_PLAYER = 0x0001;
    /** Filter category for projectiles. */
    public static final short CATEGORY_PROJECTILE = 0x0002;
    /** Filter category for Arena. */
    public static final short CATEGORY_ARENA = 0x0004;

    /** Filter mask for players.*/
    public static final short MASK_PLAYER = CATEGORY_PLAYER | CATEGORY_PROJECTILE | CATEGORY_ARENA;
    /** ** Filter mask for projectiles. */
    public static final short MASK_PROJECTILE = CATEGORY_PLAYER | CATEGORY_PROJECTILE | CATEGORY_ARENA;
    /** ** Filter mask for arena. */
    public static final short MASK_ARENA = CATEGORY_PLAYER | CATEGORY_PROJECTILE;

    /** Utility class. */
    private CollisionFiltering() { }
}
