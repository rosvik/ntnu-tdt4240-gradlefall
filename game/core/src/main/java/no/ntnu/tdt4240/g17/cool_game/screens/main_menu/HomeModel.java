package no.ntnu.tdt4240.g17.cool_game.screens.main_menu;

import com.badlogic.gdx.Gdx;

import java.io.IOException;

import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.network.NetworkSettings;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsModel;

/**
 * Model for the HomeView.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class HomeModel {

    private SettingsModel settingsModel = new SettingsModel(Gdx.app.getPreferences(SettingsModel.PREFS_NAME));

    /**
     * @return the volum for the music. In the range [0, 1].
     */
    public float getMusicVolume() {
        return settingsModel.getMusicVolume();
    }

    /**
     * @return true if music is enabled.
     */
    public boolean isMusicEnabled() {
        return settingsModel.isMusicEnabled();
    }

    /**
     * @return true if connected.
     */
    public boolean isConnectedToServer() {
        return GameClient.getNetworkClientInstance().isConnected();
    }

    /**
     * @return true if the client is currently trying to connect.
     */
    public boolean isTryingToConnectToServer() {
        return GameClient.isClientConnecting();
    }

    /** Try connecting to the server again. */
    public void retryConnection() {
        final Thread connectionThread = new Thread("Connection retry") {
            @Override
            public void run() {
                try {
                    GameClient.connectClientBlocking(GameClient.getNetworkClientInstance(),
                            NetworkSettings.getServerIp(), NetworkSettings.getPort());
                } catch (IOException ignored) { }
            }
        };
        connectionThread.setDaemon(true);
        connectionThread.start();
    }
}
