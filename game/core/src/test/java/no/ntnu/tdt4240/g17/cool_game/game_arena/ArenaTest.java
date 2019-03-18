package no.ntnu.tdt4240.g17.cool_game.game_arena;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Johannes 'rosvik' Røsvik on 3/15/2019.
 *
 * @author Johannes 'rosvik' Røsvik
 */
class ArenaTest extends GameTest {

    private Arena arena;
    private Arena emptyArena;

    @BeforeEach
    void createShouldNotThrow() {
        Batch batch = Mockito.mock(SpriteBatch.class);
        Mockito.when(batch.getColor()).thenReturn(Color.BLACK);

        assertThat(Gdx.files.internal("map2.tmx").exists(), is(true));
        assertThat(Gdx.files.internal("empty.tmx").exists(), is(true));

        arena = new Arena("map2.tmx", 16f, 32f, 20f, batch);
        emptyArena = new Arena("empty.tmx", 16f, 32f, 20f, batch);
    }

    @Test
    void renderArena() {
        arena.renderArena();
    }

    @Test
    void renderForeground() {
        arena.renderForeground();
    }

    @Test
    void getLayer() {
        assertThat(arena.getLayer("map"), is(instanceOf(MapLayer.class)));
        assertThat(arena.getLayer("background"), is(instanceOf(MapLayer.class)));
        assertThat(arena.getLayer("foreground"), is(instanceOf(MapLayer.class)));
        assertThat(arena.getLayer("layerThatDoesNotExist"), is(instanceOf(MapLayer.class)));

        assertThrows(RuntimeException.class, () -> emptyArena.getLayer("map"));
    }

    @Test
    void getTiles() {
        assertTrue(arena.getTiles()[0][0]);
    }

    @Test
    void textLayout() {
        String textLayout =
                "XXXXXXXX---XXXXXXXXXX---XXXXXXXX\n" +
                "XXXXXXXX----------------XXXXXXXX\n" +
                "X------------------------------X\n" +
                "X------------------------------X\n" +
                "X--------------XX--------------X\n" +
                "X--------------XX--------------X\n" +
                "XX-------------XX-------------XX\n" +
                "XXXX---------XXXXXX---------XXXX\n" +
                "XX----------------------------XX\n" +
                "XX----------------------------XX\n" +
                "XX---XXX----------------XXX---XX\n" +
                "X------------------------------X\n" +
                "--------------------------------\n" +
                "---------XXXXX----XXXXX---------\n" +
                "XXX--------XXX----XXX--------XXX\n" +
                "X------------------------------X\n" +
                "X------------------------------X\n" +
                "XXXX------------------------XXXX\n" +
                "XXXX----------XXXX----------XXXX\n" +
                "XXXXXXXX---XXXXXXXXXX---XXXXXXXX\n";
        assertEquals(arena.toString(), textLayout);
    }

    @Test
    void getHeight() {
        assertThat(arena.getHeight(), is(20f));
    }

    @Test
    void getWidth() {
        assertThat(arena.getWidth(), is(32f));

    }
}