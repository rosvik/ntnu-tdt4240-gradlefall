package no.ntnu.tdt4240.g17.server.game_engine;

import com.badlogic.ashley.core.Engine;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/11/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class GameEngine {

    private final Engine ecsEngine;

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
    public void run() {
        //TODO
    }
}
