package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/12/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class CharacterBox2dBodyFactoryTest {

    private World world;

    @BeforeAll
    static void setUpBox2d() {
        Box2D.init();
    }

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0f, -9.81f), true);
    }

    @Test
    void shouldCreateBody() {
        // Given
        final CharacterBox2dBodyFactory factory = new CharacterBox2dBodyFactory(world, 0.3f);
        final Entity entity = new Entity();

        // When
        final Body body = factory.create(entity);

        // Then
        assertNotNull(body, "Did not create body");
        assertTrue(body.isFixedRotation(), "Player should not rotate");
        assertThat("Has incorrect number of shapes", body.getFixtureList().size, is(1));
        assertThat("Wrong body type", body.getType(), is(BodyDef.BodyType.KinematicBody));
    }
}