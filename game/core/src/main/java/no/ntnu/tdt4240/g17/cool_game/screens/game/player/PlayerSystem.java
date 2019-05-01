package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

import java.util.Collections;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;

/**
 * A system that renders all players according to data from server (client data).
 */
@Getter
@Slf4j
public class PlayerSystem extends EntitySystem {
    private Family family;
    private ComponentMapper<PlayerComponent> character;
    private Iterable<Entity> entitiesToUpdate = Collections.emptyList();
    private ClientData clientData;

    /**
     * Find all players and projectiles.
     * @param clientData the client data from server.
     */
    public PlayerSystem(final ClientData clientData) {
        this.clientData = clientData;
        family = Family.all(PlayerComponent.class).get();
        character = ComponentMapper.getFor(PlayerComponent.class);
    }

    /**
     * Renders each object.
     *
     * @param deltaTime = time since start.
     */
    @Override
    public void update(final float deltaTime) {
        for (final Entity entity : entitiesToUpdate) {
            PlayerComponent entityCharacter = character.get(entity);
            final UpdateMessagePlayer player = clientData.getPlayerById(entityCharacter.getPlayerId());
            if (player == null) {
                log.warn("Player is null for entity {}!", entity);
                continue;
            }
//            log.trace("Updating player {} to ({}, {})", player.playerName, player.position.x, player.position.y);
            float x = player.position.x;
            //entityCharacter.getCharacter().getState().getxPosition() + 0.01f;
            float y = player.position.y;
//            entityCharacter.getCharacter().getState().getyPosition() + 0.01f;
//            10;
            entityCharacter.getCharacter().render(x, y);
        }
    }

    @Override
    public final void addedToEngine(final Engine engine) {
        entitiesToUpdate = engine.getEntitiesFor(family);
    }

    @Override
    public final void removedFromEngine(final Engine engine) {
        entitiesToUpdate = Collections.emptyList();
    }
}
