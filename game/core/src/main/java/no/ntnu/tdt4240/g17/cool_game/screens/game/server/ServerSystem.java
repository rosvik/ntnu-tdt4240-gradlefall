package no.ntnu.tdt4240.g17.cool_game.screens.game.server;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.Engine;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.ProjectileComponent;

import java.util.Collections;

/**
 * Render each object to screen.
 * @author Håvard 'havfar' Farestveit
 */
public class ServerSystem extends EntitySystem {
    private Family family;
    private ComponentMapper<PlayerComponent> character;
    private ComponentMapper<ServerComponent> posistion;
    private ComponentMapper<ProjectileComponent> projectile;
    private Iterable<Entity> entitiesToUpdate = Collections.emptyList();

    /**
     * Find all players and projectiles.
     */
    public ServerSystem() {
        family = Family.all(PlayerComponent.class).get();
        character = ComponentMapper.getFor(PlayerComponent.class);
        posistion = ComponentMapper.getFor(ServerComponent.class);
        projectile = ComponentMapper.getFor(ProjectileComponent.class);

    }

    /**
     * Renders each object.
     * @param deltaTime = time since start.
     */
    @Override
    public void update(final float deltaTime) {
        for (Entity entity : entitiesToUpdate) {
            PlayerComponent entityCharacter = character.get(entity);
            ServerComponent entityPosition = posistion.get(entity);
            ProjectileComponent entityProjectile = projectile.get(entity);
            entityPosition.fetchFromAPI();
            entityCharacter.getCharacter().render(entityPosition.getX(), entityPosition.getY());
            if (entityPosition.shootPressed()) {
                entityProjectile.shoot(entityPosition.getX(), entityPosition.getY(), entityPosition.getAngle());
            }
            entityProjectile.renderProjectiles();
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
