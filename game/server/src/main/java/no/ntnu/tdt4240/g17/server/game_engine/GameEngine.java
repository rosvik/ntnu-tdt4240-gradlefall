package no.ntnu.tdt4240.g17.server.game_engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerEntityFactory;

/**
 * This game engine simulates a single round of gameplay.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class GameEngine implements Runnable {

    private final Engine ecsEngine;

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
    }

    /**
     * Run the engine.
     */
    @Override
    public void run() {
        log.info("Starting game on engine {}", this);
        long lastUpdate = System.currentTimeMillis();
        while (!gameOver) {
            final long now = System.currentTimeMillis();
            ecsEngine.update(now - lastUpdate);
            lastUpdate = now;
            gameOver = doGameOverCheck();
        }
        log.info("Game over on engine {}", this);
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
}
