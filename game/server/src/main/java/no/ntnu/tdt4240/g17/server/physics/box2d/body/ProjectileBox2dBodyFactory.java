package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Creates bodies for projectiles.
 * <p>
 * This website as some info on arrow simulation: http://www.iforce2d.net/b2dtut/sticky-projectiles
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class ProjectileBox2dBodyFactory extends BaseBox2dBodyFactory {

    private Vector2 tailCoordinate = new Vector2(-0.4f, 0f);
    private Vector2 initialDirection = new Vector2(1f, 0);

    /**
     * Create a new factory to create projectiles.
     * Use {@link #getTailCoordinate()} and {@link #getInitialDirection()} to apply drag force etc.
     * @param world the world to create objets in
     */
    public ProjectileBox2dBodyFactory(final World world) {
        super(world);
    }

    /**
     * @return false as projectiles can rotate
     */
    @Override
    protected boolean hasFixedRotation() {
        return false;
    }

    /**
     * @return DynamicBody as arrows are affected by forces
     */
    @Override
    protected BodyDef.BodyType getType() {
        return BodyDef.BodyType.DynamicBody;
    }

    /**
     * Sets angular damping.
     *
     * @param bodyDef the bodyDef to configure
     */
    @Override
    protected void setBodyDefSettings(final BodyDef bodyDef) {
        bodyDef.angularDamping = 3f; // Seemed okay to avoid rotating too much, from the iforce2d article.
        bodyDef.bullet = true;
        bodyDef.linearDamping = 0f;
    }

    /**
     * @param body attach shapes to this
     */
    @Override
    protected void addShapes(final Body body) {
        final PolygonShape polygonShape = new PolygonShape();

        // Somewhat arrow shaped with right edge/tip at (0, 0) and tail at (-0.4, 0).
        // Points towards direction [1, 0].
        polygonShape.set(new float[] {
                tailCoordinate.x, tailCoordinate.y, // Tail
                -0.1f, -0.1f, // Bottom
                0f, 0f, // The tip
                -0.1f, 0.1f // Top
        });

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 0.05f;
        fixtureDef.friction = 0.9f;
        fixtureDef.density = 1;

        fixtureDef.shape = polygonShape;

        body.createFixture(fixtureDef);

        polygonShape.dispose();
    }

    /**
     * @return a vector with world coordinates for the tail of created arrows
     */
    public Vector2 getTailCoordinate() {
        return tailCoordinate;
    }

    /**
     * Do not alter this vector. It's value is not read.
     * @return a vector with the direction of the arrow tip
     */
    public Vector2 getInitialDirection() {
        return initialDirection;
    }
}
