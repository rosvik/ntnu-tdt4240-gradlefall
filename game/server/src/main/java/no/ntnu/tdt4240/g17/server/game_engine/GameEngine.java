package no.ntnu.tdt4240.g17.server.game_engine;

import com.badlogic.ashley.core.Engine;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/11/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class GameEngine implements Runnable {

    private final Engine ecsEngine;

    /** If the game is over or not. */
    @Getter @Setter
    private boolean isGameOver = false;

    /**
     * Create a new GameEngine for a session.
     * One GameEngine is supposed to simulate one game.
     * @param engine the ECS engine to use.
     */
    public GameEngine(final Engine engine) {
        ecsEngine = engine;
    }

    /**
     * Run the engine.
     */
    @Override
    public void run() {
        long lastUpdate = System.currentTimeMillis();
        while (!isGameOver) {
            final long now = System.currentTimeMillis();
            ecsEngine.update(now - lastUpdate);
            lastUpdate = now;
        }
    }
}
