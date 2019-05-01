package no.ntnu.tdt4240.g17.server.game_engine.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;
import no.ntnu.tdt4240.g17.server.game_engine.player.NetworkedPlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;

/**
 * Sends messages to clients.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class SendUpdateMessageSystem extends EntitySystem {

    /** The family for playerEntities this system will use. */
    public static final Family FAMILY = Family.all(
            PlayerComponent.class,
            TransformComponent.class,
            NetworkedPlayerComponent.class
    ).get();

    private final float interval;
    private final Family family;
    private ImmutableArray<Entity> playerEntities;
    private float timeAccumulator = 0f;

    /** Create a new system that sends messages at the given interval.
     * @param interval how often the messages are sent
     * @param priority system priority. Lower comes first
     * @param family the family for playerEntities. use {@link #FAMILY}.
     */
    public SendUpdateMessageSystem(final int priority, final float interval, final Family family) {
        super(priority);
        this.interval = interval;
        this.family = family;
    }

    @Override
    public void addedToEngine(final Engine engine) {
        playerEntities = engine.getEntitiesFor(family);
    }

    @Override
    public void removedFromEngine(final Engine engine) {
        playerEntities = null;
    }

    @Override
    public void update(final float deltaTime) {
        timeAccumulator += deltaTime;
        if (timeAccumulator >= interval) {
            updateInterval();
            timeAccumulator = 0f;
        }
    }

    /** Run to send network data. */
    protected void updateInterval() {
        final long beforeUpdate = System.currentTimeMillis();

        final UpdateMessage updateMessage = createMessage();

        for (final Entity entity : playerEntities) {
            final NetworkedPlayerComponent networkedPlayerComponent = NetworkedPlayerComponent.MAPPER.get(entity);
            final PlayerConnection connection = networkedPlayerComponent.getPlayerConnection();
            log.trace("Updating connection {} (IP: {}. Player id: {}). Message: {}", connection.getID(),
                    connection.getRemoteAddressTCP(), connection.getId(), updateMessage);
            if (connection.isConnected()) {
                connection.sendTCP(updateMessage);
            } else {
                PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);
                if (playerComponent.isAlive()) {
                    log.warn("Player {} disconnected! Killing him!", playerComponent.getId());
                    playerComponent.setAlive(false);
                }
            }
        }
        final long updateTime = System.currentTimeMillis() - beforeUpdate;
        log.trace("Update time: {}", updateTime);
    }

    /**
     * Create the message to send to players.
     * @return the created message
     */
    @NotNull
    UpdateMessage createMessage() {
        final UpdateMessage updateMessage = new UpdateMessage();
        updateMessage.players = new ArrayList<>(playerEntities.size());

        for (final Entity entity : playerEntities) {
            final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);
            final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
            final Vector2 position = transformComponent.getPosition();

            final UpdateMessagePlayer player = new UpdateMessagePlayer();
            player.isAlive = playerComponent.isAlive();
            player.playerId = playerComponent.getId();
            player.playerName = playerComponent.getDisplayName();
            player.position = new Position(position.x, position.y);
            player.projectileAmmoCount = playerComponent.getAmmo().size();
            player.aimingAngle = playerComponent.getAimingAngle();
            player.blockAmmoCount = 0; // FIXME: 4/30/2019 Use real value
            player.activePowerups = new ArrayList<>(); // FIXME: 5/1/2019 Use real value
            updateMessage.players.add(player);
        }

        updateMessage.projectiles = new ArrayList<>();
        // FIXME: 4/30/2019 Add projectiles to message

        return updateMessage;
    }
}
