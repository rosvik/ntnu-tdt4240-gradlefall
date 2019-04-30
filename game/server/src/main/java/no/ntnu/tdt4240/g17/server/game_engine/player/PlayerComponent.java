package no.ntnu.tdt4240.g17.server.game_engine.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.ProjectileType;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/15/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Getter @Setter
public class PlayerComponent implements Component {
    /** Component mapper for {@link PlayerComponent}. */
    public static final ComponentMapper<PlayerComponent> MAPPER = ComponentMapper.getFor(PlayerComponent.class);

    /** Session unique id. */
    private String id;
    /** The display name for the player, visible to others. */
    private String displayName;

    /** If the player is alive or not. */
    private boolean isAlive = true;
    /** The player's ammunition for projectiles. Fired in LIFO order.*/
    private List<ProjectileType> ammo = new ArrayList<>(3);
    private float aimingAngle = 0f;

    /**
     * Creates a new instance.
     * @param id the player id
     * @param displayName the player's display name
     */
    public PlayerComponent(final String id, final String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
}
