package no.ntnu.tdt4240.g17.server.physics;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/11/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class PhysicsSystemTest {

    /** Seconds */
    public static final float INTERVAL = 0.2f;
    private World world;
    private Engine engine;
    private PhysicsSystem physicsSystem;

    @BeforeAll
    static void initialize() {
        Box2D.init();
    }

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, -9.81f), true);

        engine = new Engine();
        physicsSystem = new PhysicsSystem(PhysicsSystem.PHYSICS_FAMILY, 0, INTERVAL, world);
        engine.addSystem(physicsSystem);
    }

    @AfterEach
    void tearDown() {
        world.dispose();
        world = null;
    }


    @Test
    void shouldGetExistingEntitiesFromEngine() {
        // Given
        final Entity entity1 = createEntity(world);

        // When
        engine.addEntity(entity1);

        // Then
        final Iterator<Entity> iterator = physicsSystem.entitiesToUpdate.iterator();
        assertTrue(iterator.hasNext(), "Physics system does not get entities");
        assertSame(iterator.next(), entity1, "System has incorrect entity");
    }

    @Test
    void shouldRemoveEntityWhenEntityRemovedFromEngine() {
        // Given
        final Entity entity1 = createEntity(world);

        // When
        engine.addEntity(entity1);

        final Iterator<Entity> iterator = physicsSystem.entitiesToUpdate.iterator();
        assumeTrue(iterator.hasNext(), "Physics system does not get entities");

        engine.removeEntity(entity1);

        // Then
        final Iterator<Entity> iterator1 = physicsSystem.entitiesToUpdate.iterator();
        assertFalse(iterator1.hasNext(), "Entities not removed");
    }

    @Test
    void shouldClearEntitiesWhenSystemRemovedFromEngine() {
        // Given
        final Entity entity1 = createEntity(world);

        // When
        engine.addEntity(entity1);

        final Iterator<Entity> iterator = physicsSystem.entitiesToUpdate.iterator();
        assumeTrue(iterator.hasNext(), "Physics system does not get entities");

        engine.removeSystem(physicsSystem);

        // Then
        final Iterator<Entity> iterator1 = physicsSystem.entitiesToUpdate.iterator();
        assertFalse(iterator1.hasNext(), "Entities not removed");
    }

    @Test
    void shouldSimulatePhysicsOnStep() {
        // Given
        final Entity entity = createEntity(world);
        engine.addEntity(entity);
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        final float beforeY = transformComponent.getPosition().y;

        // When
        engine.update(INTERVAL);
        final float afterY = transformComponent.getPosition().y;
        engine.update(INTERVAL);
        final float afterY2 = transformComponent.getPosition().y;

        // Then
        assertNotEquals(beforeY, afterY, "Y position not updated after a time step");
        assertNotEquals(afterY, afterY2, "Y position not updated after a time step");
    }

    private static Entity createEntity(World world) {
        final Entity entity1 = new Entity();

        final BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(1, 3);
        final Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f);
        body.createFixture(shape, 10);
        shape.dispose();

        body.setUserData(entity1);

        entity1.add(new Box2dBodyComponent(body));
        entity1.add(new TransformComponent(body.getPosition(), new Vector2(1f, 1f), body.getAngle()));
        return entity1;
    }
}