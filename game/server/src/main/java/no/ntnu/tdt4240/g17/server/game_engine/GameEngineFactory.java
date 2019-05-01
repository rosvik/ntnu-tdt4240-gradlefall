package no.ntnu.tdt4240.g17.server.game_engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.server.game_arena.ArenaUtil;
import no.ntnu.tdt4240.g17.server.game_engine.gameplay.ReceivePlayerControlsSystem;
import no.ntnu.tdt4240.g17.server.game_engine.gameplay.SendUpdateMessageSystem;
import no.ntnu.tdt4240.g17.server.game_engine.player.NetworkedPlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerEntityFactory;
import no.ntnu.tdt4240.g17.server.game_session.Player;
import no.ntnu.tdt4240.g17.server.network.messageHandler.ControlsMessageEventBus;
import no.ntnu.tdt4240.g17.server.physics.PhysicsSystem;
import no.ntnu.tdt4240.g17.server.physics.WrapAroundSystem;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.CharacterBox2dBodyFactory;

/**
 * Creates {@link GameEngine}.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class GameEngineFactory {

    private static final Vector2 GRAVITY = new Vector2(0, -9.81f);
    private static final float PHYSICS_UPDATE_SECONDS = 0.1f;
    private static final float NETWORK_UPDATE_SECONDS = PHYSICS_UPDATE_SECONDS;

    /**
     * @param arena   the arena to simulate
     * @param players players in the game session
     * @return a new GameEngine
     */
    public GameEngine create(final Arena arena, final List<Player> players) {
        final Engine engine = new Engine();


        final Rectangle bounds = ArenaUtil.getBoundsFor(arena);

        final List<String> playerIds = players.stream()
                .map(player -> player.getPlayerConnection().getId())
                .collect(Collectors.toList());
        final ConcurrentLinkedQueue<ControlsMessage> controllMessageQueue = new ConcurrentLinkedQueue<>();
        final ControlsMessageEventBus.Subscription subscription = ControlsMessageEventBus.getInstance()
                .subscribe(connection -> playerIds.contains(connection.getId()),
                        (message, connection) -> controllMessageQueue.add(message));

        final World world = new World(GRAVITY, true);


        int priority = 0;
        engine.addSystem(new WrapAroundSystem(priority++, bounds));

        engine.addSystem(new ReceivePlayerControlsSystem(ReceivePlayerControlsSystem.PLAYER_CONTROLS_FAMILY,
                priority++, controllMessageQueue));

        engine.addSystem(new PhysicsSystem(PhysicsSystem.PHYSICS_FAMILY, priority++, PHYSICS_UPDATE_SECONDS, world));

        engine.addSystem(new SendUpdateMessageSystem(priority++, NETWORK_UPDATE_SECONDS,
                SendUpdateMessageSystem.FAMILY));

        // FIXME: 4/1/2019 put this somewhere else
        final PlayerEntityFactory playerEntityFactory = new PlayerEntityFactory(
                new CharacterBox2dBodyFactory(world, 2f));
        for (Player player : players) {
            final Entity playerEntity = playerEntityFactory.create(player.getId(), player.getPlayerName());
            playerEntity.add(new NetworkedPlayerComponent(player.getPlayerConnection()));
            engine.addEntity(playerEntity);
        }

        final GameEngine gameEngine = new GameEngine(engine);
        gameEngine.addDisposable(subscription::unsubscribe);
        gameEngine.addDisposable(world);
        gameEngine.addDisposable(controllMessageQueue::clear);
        return gameEngine;
    }
}
