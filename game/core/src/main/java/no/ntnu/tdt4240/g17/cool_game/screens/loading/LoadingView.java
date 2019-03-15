package no.ntnu.tdt4240.g17.cool_game.screens.loading;

import com.badlogic.gdx.Screen;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;


/**
 *
 */
public class LoadingView implements Screen {

    /**
     * @param parent refers to the field to store the argument
     */
    private Navigator parent;

    /**
     * @param navigator referes to field over
     */
    public LoadingView(final Navigator navigator) {
        parent = navigator;
    }

    @Override
    public void show() {

    }

    /**
     * @param delta
     */
    @Override
    public void render(final float delta) {
        parent.changeView(Navigator.HOME);
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
