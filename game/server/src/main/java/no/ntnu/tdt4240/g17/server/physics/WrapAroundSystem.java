package no.ntnu.tdt4240.g17.server.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.projectile.ProjectileComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.BoundingBoxComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;

/**
 * A system to wrap players and projectiles around the arena.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class WrapAroundSystem extends IteratingSystem {

    /**
     * The family to work on.
     */
    public static final Family FAMILY = Family.all(
            Box2dBodyComponent.class,
            TransformComponent.class,
            BoundingBoxComponent.class
    ).one(PlayerComponent.class, ProjectileComponent.class)
            .get();

    private final Rectangle bounds;

    /**
     * Create a new instance of this system.
     *
     * @param priority the priority of the system
     * @param bounds   the arena bounds in world coordinates, in meters
     */
    public WrapAroundSystem(final int priority, final Rectangle bounds) {
        super(FAMILY, priority);
        this.bounds = bounds;
    }

    /**
     * Move an entity to the other side of the arena if it exits the bounds.
     *
     * @param entity    the entity to check
     * @param deltaTime seconds since last update
     */
    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        final Box2dBodyComponent bodyComponent = Box2dBodyComponent.MAPPER.get(entity);
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        final BoundingBoxComponent boundingBoxComponent = BoundingBoxComponent.MAPPER.get(entity);

        final Body body = bodyComponent.getBody();
        Vector2 position = body.getPosition();
        final Rectangle entityBounds = boundingBoxComponent.getBoundingBox();
        Vector2 newPosition = new Vector2(position);

        if (position.x + entityBounds.width < bounds.x) {
            newPosition.x = bounds.x + bounds.width;
        } else if (position.x > bounds.x + bounds.width) {
            newPosition.x = bounds.x - entityBounds.width;
        }

        if (position.y + entityBounds.height < bounds.y) {
            newPosition.y = bounds.y + bounds.height;
        } else if (position.y > bounds.y + bounds.height) {
            newPosition.y = bounds.y - entityBounds.height;
        }

        body.setTransform(newPosition, body.getAngle());
        transformComponent.setPosition(newPosition);
    }

}
