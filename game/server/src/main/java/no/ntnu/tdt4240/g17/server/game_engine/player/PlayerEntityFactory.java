package no.ntnu.tdt4240.g17.server.game_engine.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import no.ntnu.tdt4240.g17.server.physics.box2d.BoundingBoxComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.CharacterBox2dBodyFactory;

/**
 * A factory to create player entities.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class PlayerEntityFactory {

    /**
     * The family for a player entity.
     */
    public static final Family FAMILY = Family.all(
            PlayerComponent.class,
            TransformComponent.class,
            Box2dBodyComponent.class,
            BoundingBoxComponent.class
    ).get();

    private final CharacterBox2dBodyFactory bodyFactory;

    /**
     * Create a new player entity.
     *
     * @param bodyFactory a factory to create bodies.
     */
    public PlayerEntityFactory(final CharacterBox2dBodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    /**
     * Create a new player entity.
     *
     * @param playerId          the unique player id
     * @param playerDisplayName the player display name
     * @return a newly created entity
     */
    public Entity create(final String playerId, final String playerDisplayName) {
        final Entity playerEntity = new Entity();
        final Body body = bodyFactory.create(playerEntity);

        playerEntity.add(new Box2dBodyComponent(body));
        playerEntity.add(new PlayerComponent(playerId, playerDisplayName));
        playerEntity.add(new TransformComponent(new Vector2(body.getPosition()), new Vector2(1f, 1f), body.getAngle()));
        final Rectangle boundingBox = new Rectangle(0, 0,
                bodyFactory.getCharacterWidth(), bodyFactory.getCharacterHeight());
        playerEntity.add(new BoundingBoxComponent(boundingBox));

        return playerEntity;
    }
}
