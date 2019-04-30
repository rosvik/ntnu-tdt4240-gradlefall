package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerSystemTest extends GameTest {
    Entity controller;
    Engine engine;
    ControllerSystem cs;

    @BeforeEach
    void setUp() {
        controller = new Entity();
        engine = new Engine();
        engine.addEntity(controller);
        controller.add(GUI.getInstance());
        controller.add(new ControllerServerComponent());
        cs = new ControllerSystem();
        engine.addSystem(cs);
    }

    @Test
    void update() {
        cs.update(10f);
    }

    @Test
    void addedToEngine() {
        engine.addSystem(cs);
    }

    @Test
    void removedFromEngine(){
        engine.removeAllEntities();
    }

}