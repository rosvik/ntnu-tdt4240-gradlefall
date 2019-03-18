package no.ntnu.tdt4240.g17.server.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.hamcrest.MatcherAssert;
import org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.server.physics.box2d.BoundingBoxComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyToTransformComponentAdapter;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/18/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class WrapAroundSystemTest {

    private Rectangle bounds;
    private Entity entity;
    private Body bodyMock;
    private Box2dBodyToTransformComponentAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new Box2dBodyToTransformComponentAdapter();
        bounds = new Rectangle(0, 0, 5, 5);
        entity = new Entity();

        bodyMock = Mockito.mock(Body.class);
        when(bodyMock.getPosition()).thenReturn(new Vector2(0, 0));
        when(bodyMock.getAngle()).thenReturn(0f);
    }

    @Test
    void shouldWrapEntitiesLeftOfArena() {
        // Given
        final WrapAroundSystem system = new WrapAroundSystem(0, bounds);
        final Rectangle boundingBox = new Rectangle(0, 0, 1f, 1f);
        final Vector2 entityPosition = new Vector2(bounds.x - boundingBox.width - 0.01f, 0);

        final TransformComponent transformComponent = initializeSystem(boundingBox, entityPosition);

        // When
        system.processEntity(entity, 0);

        // Then
        assertThat(transformComponent.getPosition().x, is(bounds.x + bounds.width));
    }

    @Test
    void shouldWrapEntitiesRightOfArena() {
        // Given
        final WrapAroundSystem system = new WrapAroundSystem(0, bounds);
        final Rectangle boundingBox = new Rectangle(0, 0, 1f, 1f);
        final Vector2 entityPosition = new Vector2(bounds.x + bounds.width + 0.01f, 0);

        final TransformComponent transformComponent = initializeSystem(boundingBox, entityPosition);

        // When
        system.processEntity(entity, 0);

        // Then
        assertThat(transformComponent.getPosition().x, is(bounds.x - boundingBox.width));
    }

    @Test
    void shouldNotWrapXOnExactLeftEdge() {
        // Given
        final WrapAroundSystem system = new WrapAroundSystem(0, bounds);
        final Rectangle boundingBox = new Rectangle(0, 0, 1f, 1f);
        final Vector2 entityPosition = new Vector2(bounds.x - boundingBox.width, 0);

        final TransformComponent transformComponent = initializeSystem(boundingBox, entityPosition);
        final Vector2 initialPosition = transformComponent.getPosition().cpy();

        // When
        system.processEntity(entity, 0);

        // Then
        assertThat(transformComponent.getPosition().x, is(initialPosition.x));
    }

    @Test
    void shouldNotWrapXOnExactRightEdge() {
        // Given
        final WrapAroundSystem system = new WrapAroundSystem(0, bounds);
        final Rectangle boundingBox = new Rectangle(0, 0, 1f, 1f);
        final Vector2 entityPosition = new Vector2(bounds.x + bounds.width, 0);

        final TransformComponent transformComponent = initializeSystem(boundingBox, entityPosition);
        final Vector2 initialPosition = transformComponent.getPosition().cpy();

        // When
        system.processEntity(entity, 0);

        // Then
        assertThat(transformComponent.getPosition().x, is(initialPosition.x));
    }

    @NotNull
    private TransformComponent initializeSystem(final Rectangle boundingBox, final Vector2 entityPosition) {
        when(bodyMock.getPosition()).thenReturn(entityPosition);
        final Box2dBodyComponent bodyComponent = new Box2dBodyComponent(bodyMock);
        final TransformComponent transformComponent = adapter.createFrom(bodyComponent.getBody());
        entity.add(bodyComponent);
        entity.add(transformComponent);
        entity.add(new BoundingBoxComponent(boundingBox));

        Assumptions.assumeTrue(transformComponent.getPosition().x == bodyComponent.getBody().getPosition().x);
        return transformComponent;
    }
}