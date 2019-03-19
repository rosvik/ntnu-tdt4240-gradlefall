package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import lombok.Getter;
import lombok.Setter;

/**
 * A factory to create {@link Body} suitable for characters.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class CharacterBox2dBodyFactory extends BaseBox2dBodyFactory {

    private float linearDamping;

    /** The height of created characters in meters. */
    @Getter @Setter
    private float characterHeight = 1.8f;

    /** The width of created characters in meters. */
    @Getter @Setter
    private float characterWidth = 0.5f;

    /**
     * Create a factory for character body generation.
     *
     * @param world         the world to create bodies in
     * @param linearDamping velocity damping of the character
     */
    public CharacterBox2dBodyFactory(final World world, final float linearDamping) {
        super(world);
        this.linearDamping = linearDamping;
    }

    /**
     * @return true as character hitboxes do not rotate.
     */
    @Override
    protected boolean hasFixedRotation() {
        return true;
    }

    /**
     * @return Kinematic as players should be affected by forces like gravity
     */
    @Override
    protected BodyDef.BodyType getType() {
        return BodyDef.BodyType.DynamicBody;
    }

    /**
     * @param bodyDef the bodyDef to configure
     */
    @Override
    protected void setBodyDefSettings(final BodyDef bodyDef) {
        bodyDef.linearDamping = linearDamping;
    }

    /**
     * Creates a box of 1.8m height.
     *
     * @param body attach shapes to this
     */
    @Override
    protected void addShapes(final Body body) {
        final PolygonShape shape = new PolygonShape();
        // The bottom has a center point and lifted edges to avoid getting stuck between tiles.
        final float edgeHeight = 0.1f;
        shape.set(new float[]{
                0, edgeHeight,
                characterWidth / 2f, 0,
                characterWidth, edgeHeight,
                characterWidth, characterHeight,
                0, characterHeight
        });
        /* roughly 70kg weight.
        Does not matter for kinematic objects, they have 0 mass */
        final int characterDensity = 77;
        body.createFixture(shape, characterDensity);
        shape.dispose();
    }

}
