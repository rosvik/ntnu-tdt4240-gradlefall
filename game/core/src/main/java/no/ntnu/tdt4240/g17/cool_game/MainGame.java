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
    /** Background image. */
    private Texture background;
    /** Arena. */
    private Arena arena;

    /** Arena width in tiles. */
    private float width;

    /** Arena height in tiles. */
    private float height;

    @Override
    public final void create() {
        width = 32f;
        height = 20f;

        batch = new SpriteBatch();
        arena = new Arena("map2.tmx", 16f, width, height, batch);
        background = new Texture("background.png");
    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Draw the background
        batch.draw(background, 0f, 0f, width, height);

        // Render the arena
        arena.renderArena();
        arena.renderForeground();

        //batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public final void dispose() {
        batch.dispose();
        background.dispose();
    }
}
