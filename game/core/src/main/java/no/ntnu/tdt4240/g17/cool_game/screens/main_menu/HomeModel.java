package no.ntnu.tdt4240.g17.cool_game.screens.main_menu;

import com.badlogic.gdx.Gdx;

import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsModel;

/**
 * Model for the HomeView.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class HomeModel {

    private SettingsModel settingsModel = new SettingsModel(Gdx.app.getPreferences(SettingsModel.PREFS_NAME));

    /** @return the volum for the music. In the range [0, 1].*/
    public float getMusicVolume() {
        return settingsModel.getMusicVolume();
    }

    /** @return true if music is enabled. */
    public boolean isMusicEnabled() {
        return settingsModel.isMusicEnabled();
    }
}
