package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A factory to create {@link Body} suitable for characters.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class CharacterBox2dBodyFactory extends BaseBox2dBodyFactory {

    private float linearDamping;

    /**
     * Create a factory for character body generation.
     * @param world the world to create bodies in
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
     * @return Kinematic as players should be controlled by inputs, not forces
     */
    @Override
    protected BodyDef.BodyType getType() {
        //TODO: perhaps dynamic is better, unsure here.
        return BodyDef.BodyType.KinematicBody;
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
     * @param body attach shapes to this
     */
    @Override
    protected void addShapes(final Body body) {
        final PolygonShape shape = new PolygonShape();
        final float characterHeight = 1.8f;
        final float characterWidth = 0.5f;
        shape.setAsBox(characterHeight, characterWidth);
        /* roughly 70kg weight.
        Does not matter for kinematic objects, they have 0 mass */
        final int characterDensity = 77;
        body.createFixture(shape, characterDensity);
        shape.dispose();
    }
}
