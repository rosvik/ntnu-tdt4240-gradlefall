package no.ntnu.tdt4240.g17.server.game_engine.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

/**
 * A player with a network connection.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class NetworkedPlayerComponent implements Component {
    /** The mapper to get this component from an entity. */
    public static final ComponentMapper<NetworkedPlayerComponent> MAPPER = ComponentMapper
            .getFor(NetworkedPlayerComponent.class);

    @Getter @Setter
    private PlayerConnection playerConnection;

    /**
     * Create a new component.
     *
     * @param playerConnection the connection from a {@link no.ntnu.tdt4240.g17.server.network.GameServer}.
     */
    public NetworkedPlayerComponent(final PlayerConnection playerConnection) {
        this.playerConnection = playerConnection;
    }
}
