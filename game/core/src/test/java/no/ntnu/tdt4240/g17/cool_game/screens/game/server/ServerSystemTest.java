package no.ntnu.tdt4240.g17.cool_game.screens.game.server;

import com.badlogic.ashley.core.Engine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.*;

class ServerSystemTest {
    Entity character;
    Entity position;
    Entity projectile;
    Engine engine;
    ServerSystem system;

    @BeforeEach
    void setUp() {
        system = new ServerSystem();
        engine = new Engine();
    }

    @Test
    void update() {
        system.update(1f);
    }

    @Test
    void addedToEngine(){
        system.addedToEngine(engine);
    }
}