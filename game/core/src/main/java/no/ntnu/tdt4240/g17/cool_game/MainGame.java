package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * Main game class.
 */
public class MainGame extends ApplicationAdapter {
    /** Batch to render. */
    private SpriteBatch batch;
    /** Test image. */
    private Texture img;
    private Navigator navigator;

    @Override
    public final void create() {
        batch = new SpriteBatch();
    //  img = new Texture("badlogic.jpg");
        navigator = new Navigator();
        navigator.initialize();
    }

    @Override
    public final void render() {
        /*Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //batch.draw(img, 0, 0);
        batch.end();*/
        navigator.getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public final void dispose() {
        batch.dispose();
        navigator.dispose();
    }
}
