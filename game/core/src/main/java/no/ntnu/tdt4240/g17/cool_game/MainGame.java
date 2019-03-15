package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;

/**
 * Main game class.
 */
public class MainGame extends ApplicationAdapter {
    /** Batch to render. */
    private SpriteBatch batch;
    /** Test image. */
    private Texture img;
    /** Arena. */
    private Arena arena;

    @Override
    public final void create() {
        batch = new SpriteBatch();
        arena = new Arena("map2.tmx", 16f, 32f, 20f, batch);
        arena.setBackground("background.png");
    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Render the arena
        arena.render();

        //batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public final void dispose() {
        batch.dispose();
        img.dispose();
    }
}
