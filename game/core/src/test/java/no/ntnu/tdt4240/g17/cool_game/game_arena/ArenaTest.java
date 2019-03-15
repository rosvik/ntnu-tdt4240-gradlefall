package no.ntnu.tdt4240.g17.cool_game.game_arena;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.cool_game.MainGame;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Johannes 'rosvik' Røsvik on 3/15/2019.
 *
 * @author Johannes 'rosvik' Røsvik
 */
class ArenaTest extends GameTest {

    static private SpriteBatch batch;


    @BeforeAll
    static void initialize() {
        batch = Mockito.mock(SpriteBatch.class);
        Mockito.when(batch.getColor()).thenReturn(Color.BLACK);
    }

    @Test
    void createShouldNotThrow() {
        assertTrue(Gdx.files.internal("map2.tmx").exists());

        final Arena arena = new Arena("map2.tmx", 16f, 32f, 20f, batch);
        arena.render();

        arena.printTiles();
    }


}