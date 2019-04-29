package no.ntnu.tdt4240.g17.cool_game.screens.game.projectile;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import lombok.Getter;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import java.util.Collections;

/**
 * Lombok.
 */
@Getter
/**
 * System that renders each projectile.
 */
public class ProjectileSystem extends EntitySystem {
    private Family family;
    private ComponentMapper<ProjectileComponent> projectile;
    private Iterable<Entity> entitiesToUpdate;
    private ClientData clientData;

    /**
     * Find all players and projectiles.
     */
    public ProjectileSystem() {
        family = Family.all(ProjectileComponent.class).get();
        projectile = ComponentMapper.getFor(ProjectileComponent.class);
        clientData = new ClientData();
        entitiesToUpdate = Collections.emptyList();
    }

    /**
     * Renders each object.
     * Calls clientData and get projectile by the entity Id.
     * @param deltaTime = time since start.
     */
    @Override
    public void update(final float deltaTime) {
        for (Entity entity : entitiesToUpdate) {
            ProjectileComponent entityProjectile = projectile.get(entity);
            float x = clientData.getProjectileById(entityProjectile.getProjectileId()).projectilePosition.x;
            float y = clientData.getProjectileById(entityProjectile.getProjectileId()).projectilePosition.y;
            float angle = clientData.getProjectileById(entityProjectile.getProjectileId()).projectileAngle;
            entityProjectile.render(x, y, angle);
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
