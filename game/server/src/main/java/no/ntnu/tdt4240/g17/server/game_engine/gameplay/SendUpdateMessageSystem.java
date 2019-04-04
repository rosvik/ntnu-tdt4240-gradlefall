package no.ntnu.tdt4240.g17.server.game_engine.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;
import no.ntnu.tdt4240.g17.server.game_engine.player.NetworkedPlayerComponent;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

/**
 * Sends messages to clients.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class SendUpdateMessageSystem extends IntervalSystem {

    public static final Family FAMILY = Family.all(
            NetworkedPlayerComponent.class
    ).get();
    private final Family family;

    private ImmutableArray<Entity> entities;

    public SendUpdateMessageSystem(final int priority, final float interval, final Family family) {
        super(interval, priority);
        this.family = family;
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
    protected void updateInterval() {
        // TODO: 4/1/2019 Snapshot all game state here
        final UpdateMessage updateMessage = new UpdateMessage();
        updateMessage.players = new ArrayList<>();
        final UpdateMessagePlayer player = new UpdateMessagePlayer();
        player.isAlive = true;
        player.position = new Position(2, 2);
        player.playerId = "1";
        player.playerName = "Test";
        player.projectileAmmoCount = 3;
        updateMessage.players.add(player);

        for (final Entity entity : entities) {
            final NetworkedPlayerComponent networkedPlayerComponent = NetworkedPlayerComponent.MAPPER.get(entity);
            final PlayerConnection connection = networkedPlayerComponent.getPlayerConnection();
            log.trace("Updating connection {} (IP: {})", connection.getID(), connection.getRemoteAddressTCP());
            connection.sendTCP(updateMessage);
        }
    }
}
