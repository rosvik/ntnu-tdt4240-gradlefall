package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Creates bodies for a single arena tile.
 * They are squares with bottom left corner at (0,0)
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class ArenaTileBox2dBodyFactory extends BaseBox2dBodyFactory {

    private final float boxHeightMeters;
    private final float boxWidthMeters;

    /**
     * Creates bodies for arena tiles, which are immovable.
     *
     * @param world the world to create bodies in
     * @param boxHeightMeters height of the box in meters
     * @param boxWidthMeters width of the box in meters
     */
    public ArenaTileBox2dBodyFactory(final World world, final float boxHeightMeters, final float boxWidthMeters) {
        super(world);
        this.boxHeightMeters = boxHeightMeters;
        this.boxWidthMeters = boxWidthMeters;
    }

    /**
     * @return true as the world tiles cant rotate
     */
    @Override
    protected boolean hasFixedRotation() {
        return true;
    }

    /**
     * @return StaticBody as the world tiles can't be moved by objects
     */
    @Override
    protected BodyDef.BodyType getType() {
        return BodyDef.BodyType.StaticBody;
    }

    @Override
    protected void setBodyDefSettings(final BodyDef bodyDef) {
    }

    /**
     * Adds a single box with (0,0) at bottom left.
     *
     * @param body attach shapes to this
     */
    @Override
    protected void addShapes(final Body body) {
        PolygonShape shape = new PolygonShape();
        shape.set(new float[] {
                0, 0,
                boxWidthMeters, 0,
                boxWidthMeters, boxHeightMeters,
                0, boxHeightMeters
        });
        body.createFixture(shape, 1f);
        shape.dispose();
    }

    /**
     * @return the height of created boxes in meters
     */
    public float getBoxHeightMeters() {
        return boxHeightMeters;
    }

    /**
     * @return the width of created boxes in meters
     */
    public float getBoxWidthMeters() {
        return boxWidthMeters;
    }
}
