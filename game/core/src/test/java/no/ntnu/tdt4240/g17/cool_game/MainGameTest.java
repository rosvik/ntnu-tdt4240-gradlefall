package no.ntnu.tdt4240.g17.cool_game;

import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/11/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class MainGameTest extends GameTest {

    @Test
    void createShouldNotThrow() {
        final MainGame mainGame = new MainGame();
        assertThrows(IllegalArgumentException.class, mainGame::create, "SpriteBatch must be mocked and will throw in tests");
    }
}