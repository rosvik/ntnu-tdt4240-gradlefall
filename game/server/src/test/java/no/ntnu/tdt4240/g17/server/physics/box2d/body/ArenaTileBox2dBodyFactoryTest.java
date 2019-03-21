package no.ntnu.tdt4240.g17.server.physics.box2d.body;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/15/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class ArenaTileBox2dBodyFactoryTest {

    private World world;

    @BeforeAll
    static void setUpBox2d() {
        Box2D.init();
    }

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, -9.81f), true);
    }

    @Test
    void shouldHaveBottomLeftCornerInOrigo() {
        // Given
        final Entity entity = new Entity();
        final ArenaTileBox2dBodyFactory factory = new ArenaTileBox2dBodyFactory(world, 1f, 1f);
        final float width = factory.getBoxWidthMeters();
        final float height = factory.getBoxHeightMeters();

        // When
        final Body body = factory.create(entity);

        // Then
        final Fixture fixture = body.getFixtureList().get(0);
        assertTrue(fixture.testPoint(0, 0), "Corner is not inside");
        assertFalse(fixture.testPoint(-width/2f, height/2f), "Box exists left of origo");
        assertFalse(fixture.testPoint(width/2f, -height/2f), "Box exists below origo");
    }

    @Test
    void shouldBeOnlyOneShape() {
        // Given
        final Entity entity = new Entity();
        final ArenaTileBox2dBodyFactory factory = new ArenaTileBox2dBodyFactory(world, 1f, 1f);

        // When
        final Body body = factory.create(entity);

        // Then
        assertThat(body.getFixtureList().size, is(1));
    }
}