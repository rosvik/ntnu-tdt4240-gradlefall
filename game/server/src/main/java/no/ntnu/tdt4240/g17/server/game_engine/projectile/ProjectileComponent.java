package no.ntnu.tdt4240.g17.server.game_engine.projectile;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/18/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Getter @Setter
public class ProjectileComponent implements Component {
    /** Mapper to get this component from {@link Entity entities}. */
    public static final ComponentMapper<ProjectileComponent> MAPPER = ComponentMapper.getFor(ProjectileComponent.class);

    @Nullable
    private Entity shotFromEntity;

    /**
     * Create a new projectile component.
     * It keeps track of who fired it and other projectile relevant data.
     * @param shotFromEntity the entity that fired the projectile, or null
     *                       if it was just spawned into the world.
     */
    public ProjectileComponent(@Nullable final Entity shotFromEntity) {
        this.shotFromEntity = shotFromEntity;
    }
}
