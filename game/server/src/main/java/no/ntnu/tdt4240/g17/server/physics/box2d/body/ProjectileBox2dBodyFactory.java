package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import lombok.Getter;

/**
 * Creates bodies for projectiles.
 * <p>
 * This website as some info on arrow simulation: http://www.iforce2d.net/b2dtut/sticky-projectiles
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class ProjectileBox2dBodyFactory extends BaseBox2dBodyFactory {

    /**
     * A vector with local coordinates for the tail of created arrows.
     */
    @Getter
    private Vector2 tailCoordinate = new Vector2(0f, 0f);

    private Vector2 initialDirection = new Vector2(1f, 0);

    /** The bounding box for a projectile. */
    @Getter
    private Rectangle boundingBox;

    /**
     * Values are stored in (x, y) order.
     */
    private final float[] shapeTemplate;

    /**
     * Create a new factory to create projectiles.
     * Use {@link #getTailCoordinate()} and {@link #getInitialDirection()} to apply drag force etc.
     *
     * @param world the world to create objets in
     */
    public ProjectileBox2dBodyFactory(final World world) {
        super(world);

        // Somewhat arrow shaped with left edge/tail at (0, 0) and tip at (0.4, 0).
        // Points towards direction [1, 0].
        float arrowThickness = 0.05f;
        float arrowLength = 0.6f;
        float thickPoint = 0.5f;
        shapeTemplate = new float[] {
                0f, 0f, // Tail
                thickPoint, -arrowThickness, // Bottom
                arrowLength, 0f, // The tip
                thickPoint, arrowThickness // Top
        };

        // Depends on tailCoordinate, so this must be moved if tail is moved.
        boundingBox = new Rectangle(0f, -thickPoint, arrowLength, thickPoint);
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
        bodyDef.linearDamping = 0.4f;
    }

    /**
     * @param body attach shapes to this
     */
    @Override
    protected void addShapes(final Body body) {
        final PolygonShape polygonShape = new PolygonShape();

        // Translate all points to start from tailCoordinate.
        final float[] points = shapeTemplate.clone();
        for (int i = 0; i < points.length / 2; i += 2) {
            points[i] += tailCoordinate.x;
            points[i + 1] += tailCoordinate.y;
        }
        polygonShape.set(points);

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 0.05f;
        fixtureDef.friction = 0.9f;
        fixtureDef.density = 1;
        fixtureDef.filter.categoryBits = CollisionFiltering.CATEGORY_PROJECTILE;
        fixtureDef.filter.maskBits = CollisionFiltering.MASK_PROJECTILE;
        fixtureDef.shape = polygonShape;

        body.createFixture(fixtureDef);

        polygonShape.dispose();
    }

    /**
     * Do not alter this vector. It's value is not read.
     *
     * @return a vector with the direction of the arrow tip
     */
    public Vector2 getInitialDirection() {
        return initialDirection;
    }
}
