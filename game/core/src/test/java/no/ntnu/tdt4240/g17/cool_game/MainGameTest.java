package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the main game class.
 * Note that it is not very testable yet,
 * mostly because of SpriteBatch and {@link MainGame#create()}.
 *
 * It could be migrated to a dependency-injection approach or similar,
 * but right now the fields are just overwritten using package protected access.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class MainGameTest extends GameTest {

    private MainGame mainGame;

    @BeforeEach
    void setUp() {
        mainGame = new MainGame();
        mainGame.batch = Mockito.mock(SpriteBatch.class);
    }

    @Test
    void createShouldNotThrow() {
        assertThrows(IllegalArgumentException.class, mainGame::create, "SpriteBatch must be mocked and will throw in tests");
    }


    @Test
    void disposeShouldNotThrow() {
        // Given

        // When
        mainGame.dispose();
    }
}