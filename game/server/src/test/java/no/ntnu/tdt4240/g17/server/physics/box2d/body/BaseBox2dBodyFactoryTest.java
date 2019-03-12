package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/12/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class BaseBox2dBodyFactoryTest {

    private World world;

    @BeforeAll
    static void loadBox2d() {
        Box2D.init();
    }

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, -9.81f), true);
    }

    @AfterEach
    void tearDown() {
        world.dispose();
        world = null;
    }

    @Test
    void shouldUseAbstractMethodsWhenCreatingBody() {
        // Given
        BaseBox2dBodyFactory factory = new BaseBox2dBodyFactory(world) {
            @Override
            protected boolean hasFixedRotation() {
                return false;
            }

            @Override
            protected BodyDef.BodyType getType() {
                return BodyDef.BodyType.DynamicBody;
            }

            @Override
            protected void setBodyDefSettings(BodyDef bodyDef) {

            }

            @Override
            protected void addShapes(Body body) {

            }
        };
        factory = Mockito.spy(factory);
        final Entity entity = new Entity();

        // When
        final Body body = factory.create(entity);

        // Then
        verify(factory).hasFixedRotation();
        verify(factory).getType();
        verify(factory, times(1)).addShapes(body);
        verify(factory).setBodyDefSettings(notNull());
    }
}