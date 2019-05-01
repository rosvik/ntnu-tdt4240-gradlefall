package no.ntnu.tdt4240.g17.server.game_engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerEntityFactory;

/**
 * This game engine simulates a single round of gameplay.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class GameEngine implements Runnable, Disposable {

    private static final AtomicInteger ENGINE_CREATION_COUNTER = new AtomicInteger(0);
    private final int id = ENGINE_CREATION_COUNTER.incrementAndGet();

    private final Engine ecsEngine;
    private final ArrayList<Disposable> disposables = new ArrayList<>(4);

    /** If the game is over or not. */
    private boolean gameOver = false;
    private final ImmutableArray<Entity> players;

    /**
     * Create a new GameEngine for a session.
     * One GameEngine is supposed to simulate one game.
     * @param engine the ECS engine to use.
     */
    public GameEngine(final Engine engine) {
        ecsEngine = engine;
        players = ecsEngine.getEntitiesFor(PlayerEntityFactory.FAMILY);

        log.debug("Created engine {}.", id);
    }

    /** @param disposable will be disposed when this gameEngine is disposed*/
    public void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    /**
     * Run the engine.
     */
    @Override
    public void run() {
        log.info("Starting game on engine {}", id);
        long lastUpdate = System.currentTimeMillis();
        long tickCount = 0;
        while (!gameOver) {
            final long now = System.currentTimeMillis();
            ecsEngine.update(now - lastUpdate);
            lastUpdate = now;
            gameOver = doGameOverCheck();

            tickCount++;
            if (tickCount % 2000 == 0) {
                log.debug("GameEngine {} is currently running...", id);
            }
        }
        log.info("Game over on engine {}", id);
        // TODO: 4/30/2019 Add a callback or similiar to start new games.
        dispose();
    }

    /** Check whether the game is over or not.
     * It is deemed over when one player is alive, or all are dead.
     * @return true if the game is over, false otherwise.
     */
    boolean doGameOverCheck() {
        int alivePlayers = 0;
        for (final Entity player : players) {
            final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(player);
            if (playerComponent.isAlive()) {
                alivePlayers += 1;
            }
        }
        return alivePlayers <= 1;
    }

    @Override
    public void dispose() {
        log.info("Disposing game engine {}", id);
        disposables.forEach(Disposable::dispose);
        disposables.clear();
    }
}
