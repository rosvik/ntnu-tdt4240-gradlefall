package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.network.NetworkSettings;
import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * Main game class.
 */
public class MainGame extends ApplicationAdapter {

    private Navigator navigator;
    @Override
    public final void create() {
        navigator = new Navigator();

        new Thread(() -> {
            final Client networkClient = GameClient.getNetworkClientInstance();
            try {
                GameClient.connectClientBlocking(networkClient,
                        NetworkSettings.getServerIp(), NetworkSettings.getPort());
            } catch (IOException e) { }
        }).start();
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
