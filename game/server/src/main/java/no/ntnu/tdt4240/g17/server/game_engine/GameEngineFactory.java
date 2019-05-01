package no.ntnu.tdt4240.g17.server.game_engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.server.game_arena.ArenaUtil;
import no.ntnu.tdt4240.g17.server.game_arena.PlayerStartPosition;
import no.ntnu.tdt4240.g17.server.game_engine.gameplay.ReceivePlayerControlsSystem;
import no.ntnu.tdt4240.g17.server.game_engine.gameplay.SendUpdateMessageSystem;
import no.ntnu.tdt4240.g17.server.game_engine.player.NetworkedPlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerEntityFactory;
import no.ntnu.tdt4240.g17.server.game_session.Player;
import no.ntnu.tdt4240.g17.server.network.messageHandler.ControlsMessageEventBus;
import no.ntnu.tdt4240.g17.server.physics.PhysicsSystem;
import no.ntnu.tdt4240.g17.server.physics.WrapAroundSystem;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.ArenaTileBox2dBodyFactory;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.CharacterBox2dBodyFactory;

/**
 * Creates {@link GameEngine}.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class GameEngineFactory {

    private static final Vector2 GRAVITY = new Vector2(0, -9.81f);
    private static final float PHYSICS_UPDATE_SECONDS = 1 / 120f;
    private static final float NETWORK_UPDATE_SECONDS = 1 / 30f;

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
                        (message, connection) -> {
                            synchronized (controllMessageQueue) {
                                controllMessageQueue.add(message);
                            }
                        });

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
        final ArrayList<Vector2> startPositions = new PlayerStartPosition(ArenaUtil.getFilePathFor(arena))
                .getStartPositions();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            final Entity playerEntity = playerEntityFactory.create(player.getId(), player.getPlayerName());
            playerEntity.add(new NetworkedPlayerComponent(player.getPlayerConnection()));

            final Vector2 startPosition = startPositions.get(i);
            final Body body = Box2dBodyComponent.MAPPER.get(playerEntity).getBody();
            body.setTransform(startPosition.x, startPosition.y, 0f);
            TransformComponent.MAPPER.get(playerEntity).setPosition(startPosition);
            log.debug("Starting player {} at position ({}, {})", player.getId(), startPosition.x, startPosition.y);

            engine.addEntity(playerEntity);
        }

        addArenaEntities(arena, engine, world);

        final GameEngine gameEngine = new GameEngine(engine);
        gameEngine.setWorld(world);
        gameEngine.addDisposable(subscription::unsubscribe);
        gameEngine.addDisposable(world);
        gameEngine.addDisposable(controllMessageQueue::clear);
        return gameEngine;
    }

    /**
     * @param arena arena to create
     * @param engine engine to add arena entities to
     * @param world world to create physics bodies in
     */
    private static void addArenaEntities(final Arena arena, final Engine engine, final World world) {
        // FIXME: 5/1/2019 Read arena file and create it in box2d
        final ArenaTileBox2dBodyFactory arenaFactory = new ArenaTileBox2dBodyFactory(world, 1f, 1f);

        for (int x = -5; x < 50; x++) {
            final Entity entity = new Entity();
            final Body body = arenaFactory.create(entity);
            body.setTransform(x, -1, 0f);
            entity.add(new Box2dBodyComponent(body));
            engine.addEntity(entity);
        }
    }
}
