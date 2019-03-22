package no.ntnu.tdt4240.g17.server.game_engine.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.server.physics.PhysicsSystem;
import no.ntnu.tdt4240.g17.server.physics.WrapAroundSystem;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.CharacterBox2dBodyFactory;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/18/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class PlayerEntityFactoryTest {

    private CharacterBox2dBodyFactory bodyFactory;
    private Body mockBody;

    @BeforeEach
    void setUp() {
        bodyFactory = Mockito.mock(CharacterBox2dBodyFactory.class);
        mockBody = Mockito.mock(Body.class);
        when(mockBody.getPosition()).thenReturn(new Vector2(0f, 0f));
        when(mockBody.getAngle()).thenReturn(0f);

        when(bodyFactory.create(any())).thenReturn(mockBody);
    }

    @Test
    void shouldCreateValidEntity() {
        // Given
        final PlayerEntityFactory factory = new PlayerEntityFactory(bodyFactory);

        // When
        final Entity entity = factory.create("1", "Test Player");

        // Then
        assertTrue(PlayerEntityFactory.FAMILY.matches(entity),
                "Entity is missing components. Has: " + entity.getComponents().toString());
    }

    @Test
    void shouldHaveComponentsNeededForSystems() {
        // Given
        final PlayerEntityFactory playerEntityFactory = new PlayerEntityFactory(bodyFactory);
        final Entity playerEntity = playerEntityFactory.create("1", "Test player");

        // When
        final boolean wrapAroundMatches = WrapAroundSystem.FAMILY.matches(playerEntity);
        final boolean physicsMatches = PhysicsSystem.PHYSICS_FAMILY.matches(playerEntity);

        // Then
        assertTrue(wrapAroundMatches, "Player is missing a component for WrapAroundSystem");
        assertTrue(physicsMatches, "Player is missing a component for PhysicsSystem");
    }
}