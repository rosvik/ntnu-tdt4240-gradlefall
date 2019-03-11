package no.ntnu.tdt4240.g17.server.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/11/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class PhysicsSystem extends IteratingSystem {

    /**
     * Family of components the engine should work on.
     */
    public static final Family PHYSICS_FAMILY = Family.all(Box2dBodyComponent.class).get();
    private final World world;

    /**
     * Create a new Physics system for an {@link com.badlogic.ashley.core.Engine}.
     * @param family the family to work with. Use {@link #PHYSICS_FAMILY}
     * @param priority the priority of the system in {@link com.badlogic.ashley.core.Engine}.
     * @param world {@link com.badlogic.gdx.physics.box2d.Box2D} world.
     */
    public PhysicsSystem(final Family family, final int priority, final World world) {
        super(family, priority);
        this.world = world;
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        //TODO: implement
    }
}
