package no.ntnu.tdt4240.g17.server.game_engine.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.HashMap;
import java.util.Queue;

import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;

/**
 * Processes player controls coming from network.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class ReceivePlayerControlsSystem extends EntitySystem {

    /** Engine family. */
    public static final Family PLAYER_CONTROLS_FAMILY = Family.all(
            PlayerComponent.class
    ).get();

    private final Family family;
    private final Queue<ControlsMessage> lastControlsMessages;
    private ImmutableArray<Entity> entities;

    /**
     * @param family the ecs family. Use {@link #PLAYER_CONTROLS_FAMILY}.
     * @param priority Engine priority.
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
        if (!lastControlsMessages.isEmpty()) {
            // Prepare entities for easy lookup
            final int size = entities.size();
            final HashMap<String, Entity> idMap = new HashMap<>();
            for (int i = 0; i < size; i++) {
                final Entity entity = entities.get(i);
                final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);
                idMap.put(playerComponent.getId(), entity);
            }

            // Read control messages
            ControlsMessage controlsMessage = null;
            while ((controlsMessage = lastControlsMessages.poll()) != null) {
                final Entity playerEntity = idMap.get(controlsMessage.playerId);

                if (controlsMessage.jump) {
                    // FIXME make this an update of an action component or similar
                    final Box2dBodyComponent bodyComponent = Box2dBodyComponent.MAPPER.get(playerEntity);
                    final int jumpImpulse = 2000;
                    bodyComponent.getBody().applyLinearImpulse(0, jumpImpulse, 0, 0, true);
                }
            }
        }
    }
}
