package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
    private float characterHeight = 1f;

    /** The width of created characters in meters. */
    @Getter @Setter
    private float characterWidth = 1f;

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
        bodyDef.allowSleep = false;
        bodyDef.bullet = true;
    }

    /**
     * Creates a box with dimensions of {@link #getCharacterWidth()} and {@link #getCharacterHeight()}.
     *
     * @param body attach shapes to this
     */
    @Override
    protected void addShapes(final Body body) {
        final PolygonShape shape = new PolygonShape();
        // The bottom has a center point and lifted edges to avoid getting stuck between tiles.
        final float edgeHeight = 0.15f;
        shape.set(new float[]{
                0, edgeHeight,
                characterWidth / 2f, 0,
                characterWidth, edgeHeight,
                characterWidth, characterHeight,
                0, characterHeight
        });
        final int characterDensity = 100; // Determines mass, and how fast they fall.
        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = CollisionFiltering.CATEGORY_PLAYER;
        fixtureDef.filter.maskBits = CollisionFiltering.MASK_PLAYER;
        fixtureDef.density = characterDensity;
        fixtureDef.friction = 0.8f;
        body.createFixture(fixtureDef);

        shape.dispose();
    }

}
