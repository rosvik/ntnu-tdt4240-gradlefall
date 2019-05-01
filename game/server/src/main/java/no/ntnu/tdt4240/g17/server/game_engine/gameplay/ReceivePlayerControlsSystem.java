package no.ntnu.tdt4240.g17.server.game_engine.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.ProjectileType;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;

/**
 * Processes player controls coming from network.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class ReceivePlayerControlsSystem extends EntitySystem {

    /**
     * Engine family.
     */
    public static final Family PLAYER_CONTROLS_FAMILY = Family.all(
            PlayerComponent.class
    ).get();

    private final Family family;
    private final Queue<ControlsMessage> lastControlsMessages;
    private ImmutableArray<Entity> entities;

    /**
     * @param family               the ecs family. Use {@link #PLAYER_CONTROLS_FAMILY}.
     * @param priority             Engine priority.
     * @param controllMessageQueue the queue to add new player controll messages
     */
    public ReceivePlayerControlsSystem(final Family family, final int priority,
                                       final Queue<ControlsMessage> controllMessageQueue) {
        super(priority);
        this.family = family;
        this.lastControlsMessages = controllMessageQueue;
    }

    @Override
    public void addedToEngine(final Engine engine) {
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void removedFromEngine(final Engine engine) {
        entities = null;
    }

    @Override
    public void update(final float deltaTime) {
        // TODO: 5/1/2019 Add some mechanism to avoid starvation.
        // The amount of processed messages could be capped,
        // eg. max 5 per player, or total of 50 messages or 5ms processing etc.
        if (!lastControlsMessages.isEmpty()) {
            // Prepare entities for easy lookup
            final int size = entities.size();
            final HashMap<String, Entity> idMap = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                final Entity entity = entities.get(i);
                final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);
                idMap.put(playerComponent.getId(), entity);
            }

            // Read control messages
            ControlsMessage controlsMessage = null;
            while ((controlsMessage = lastControlsMessages.poll()) != null) {
                processMessage(idMap, controlsMessage);
            }
        }
    }

    /** Interprets what a player wanted to do in a given message.
     * @param idMap map of players and id, for easy lookup
     * @param controlsMessage the message to interpret
     */
    void processMessage(final HashMap<String, Entity> idMap, final ControlsMessage controlsMessage) {
        final Entity playerEntity = idMap.get(controlsMessage.playerId);
        if (playerEntity == null) {
            log.error("Failed to find a player with id '{}' in map {}", controlsMessage.playerId, idMap);
            return;
        }

        if (controlsMessage.jump) {
            // FIXME make this an update of an action component or similar
            final int jumpImpulse = 2000;
            final Body body = Box2dBodyComponent.MAPPER.get(playerEntity).getBody();
            body.applyLinearImpulse(0, jumpImpulse, 0, 0, true);
        }
        if (controlsMessage.moveSpeed != 0f) {
            final Vector2 velocity = new Vector2(1f, 0);
            final float playerMaxSpeed = 7f;
            final Body body = Box2dBodyComponent.MAPPER.get(playerEntity).getBody();
            velocity.setLength(playerMaxSpeed * controlsMessage.moveSpeed / 100f);
            velocity.setAngle(controlsMessage.moveAngle);
            velocity.y = body.getLinearVelocity().y;
            body.setLinearVelocity(velocity.x, 0f);
        }
        if (controlsMessage.shoot) {
            final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(playerEntity);
            final List<ProjectileType> ammo = playerComponent.getAmmo();
            if (ammo.size() > 0) {
                final ProjectileType projectileType = ammo.remove(ammo.size() - 1);
                // TODO: 4/30/2019 create projectile
                log.warn("Missing logic: firing projectiles! Wanted to fire from user {}",
                        playerComponent.getId());
            }
        }
    }
}
