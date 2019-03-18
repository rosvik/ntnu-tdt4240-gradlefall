package no.ntnu.tdt4240.g17.server.game_engine.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/15/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Getter @Setter @AllArgsConstructor
public class PlayerComponent implements Component {
    /** Component mapper for {@link PlayerComponent}. */
    public static final ComponentMapper<PlayerComponent> MAPPER = ComponentMapper.getFor(PlayerComponent.class);

    /** Session unique id. */
    private String id;
    /** The display name for the player, visible to others. */
    private String displayName;
}
