package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

import java.util.Collections;

import lombok.Getter;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;

/**
 * A system that renders all players according to data from server (client data).
 */
@Getter
public class PlayerSystem extends EntitySystem {
    private Family family;
    private ComponentMapper<PlayerComponent> character;
    private Iterable<Entity> entitiesToUpdate = Collections.emptyList();
    private ClientData clientData;

    /**
     * Find all players and projectiles.
     */
    public PlayerSystem() {
        family = Family.all(PlayerComponent.class).get();
        character = ComponentMapper.getFor(PlayerComponent.class);
        clientData = new ClientData();
    }

    /**
     * Renders each object.
     * @param deltaTime = time since start.
     */
    @Override
    public void update(final float deltaTime) {
        for (Entity entity : entitiesToUpdate) {
            PlayerComponent entityCharacter = character.get(entity);
            float x = entityCharacter.getCharacter().getState().getxPosition() + 0.01f;//clientData.getPlayerById(entityCharacter.getPlayerId()).position.x;
            float y = 10;//entityCharacter.getCharacter().getState().getyPosition() + 0.01f;//clientData.getPlayerById(entityCharacter.getPlayerId()).position.y;
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
