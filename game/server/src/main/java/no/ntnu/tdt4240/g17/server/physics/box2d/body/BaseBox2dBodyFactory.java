package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/12/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public abstract class BaseBox2dBodyFactory {

    private final World world;

    /**
     * @param world all bodies {@link #create(Entity) created} will belong to this world
     */
    public BaseBox2dBodyFactory(final World world) {
        this.world = world;
    }

    /**
     * Create a new body for the given entity.
     * Belongs to a specific box2d {@link World}.
     * @param entity will be set as {@link Body#getUserData() userData} for the body
     * @return newly created body.
     */
    public final Body create(final Entity entity) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = hasFixedRotation();
        bodyDef.type = getType();
        setBodyDefSettings(bodyDef);
        final Body body = world.createBody(bodyDef);

        addShapes(body);

        body.setUserData(entity);
        return body;
    }

    /**
     * @return true if the body cannot rotate, false otherwise
     */
    protected abstract boolean hasFixedRotation();

    /**
     * @return the type of body:
     *  {@link BodyDef.BodyType#StaticBody} for terrain etc,<br/>
     *  {@link BodyDef.BodyType#KinematicBody} for platforms, possibly players,<br/>
     *  {@link BodyDef.BodyType#DynamicBody} for objects like arrows.
     */
    protected abstract BodyDef.BodyType getType();

    /**
     * Set initial settings here, likt {@link BodyDef#position} and {@link BodyDef#bullet}.
     * @param bodyDef the bodyDef to configure
     */
    protected abstract void setBodyDefSettings(BodyDef bodyDef);

    /**
     * Add {@link com.badlogic.gdx.physics.box2d.Shape shapes} to the body using
     * {@link com.badlogic.gdx.physics.box2d.FixtureDef fixtures}
     * or {@link Body#createFixture(com.badlogic.gdx.physics.box2d.Shape, float)}.
     *
     * Shapes can be {@link com.badlogic.gdx.physics.box2d.Shape#dispose() disposed} after they are added.
     * @param body attach shapes to this
     */
    protected abstract void addShapes(Body body);

}
