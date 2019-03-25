package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Johannes Tomren Røsvik (@rosvik) on 3/25/2019.
 *
 * @author Johannes Tomren Røsvik (@rosvik)
 */
class PlayerStartPositionTest {

    private PlayerStartPosition playerStartPosition;

    @BeforeEach
    void setUp() {
        playerStartPosition = new PlayerStartPosition("map2.tmx");
    }

    @Test
    void initializer() {
        assertDoesNotThrow(() -> playerStartPosition = new PlayerStartPosition("map3.tmx"));
        assertThrows(RuntimeException.class, () -> new PlayerStartPosition("not_a_file.hek"));
    }

    @Test
    void getStartPositions() {
        playerStartPosition.getStartPositions();
    }

    @Test
    void getPlayerStartPosition() {
        assertThat(playerStartPosition.getPlayerStartPosition(1), isA(Vector2.class));
        assertThrows(RuntimeException.class, () -> playerStartPosition.getPlayerStartPosition(5));
    }
}