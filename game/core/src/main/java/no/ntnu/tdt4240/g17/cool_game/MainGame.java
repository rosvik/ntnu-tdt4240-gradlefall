package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.cool_game.network.ConnectToServerThread;
import no.ntnu.tdt4240.g17.cool_game.network.DiscoverAndConnectToLanServerThread;
import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * Main game class.
 */
@Slf4j
public class MainGame extends ApplicationAdapter {

    private Navigator navigator;
    @Override
    public final void create() {
        navigator = new Navigator();

        new ConnectToServerThread().run();
        new DiscoverAndConnectToLanServerThread().start();
    }

    @Override
    public final void render() {
        navigator.getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public final void dispose() {
        if (navigator != null) {
            navigator.dispose();
        }
    }
}
