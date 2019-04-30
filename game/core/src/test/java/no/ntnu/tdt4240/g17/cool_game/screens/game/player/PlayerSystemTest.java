package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.ashley.core.Engine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerSystemTest {

    PlayerSystem system;
    Engine engine;

    @BeforeEach
    void setUp() {
        engine = new Engine();
        system = new PlayerSystem();
        engine.addSystem(system);
    }

    @Test
    void update() {
        system.update(1f);
    }

    @Test
    void addedToEngine() {
        system.addedToEngine(engine);
    }

    @Test
    void removedFromEngine() {
        system.removedFromEngine(engine);
    }

    @Test
    void shouldGet(){
        system.getFamily();
        system.getCharacter();
        system.getClientData();
    }
}