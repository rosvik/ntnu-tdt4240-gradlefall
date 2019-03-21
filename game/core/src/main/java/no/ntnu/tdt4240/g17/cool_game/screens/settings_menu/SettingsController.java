package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;


/**
 * Controller for {@link SettingsView}.
 */
public class SettingsController {

    private final SettingsModel model;

    /**
     * Create a new controller.
     * @param model the model to use.
     */
    public SettingsController(final SettingsModel model) {
        this.model = model;
    }

    /**
     * @return name
     */
    public static Preferences getPrefs() {
        return Gdx.app.getPreferences(SettingsModel.PREFS_NAME);
    }

    /**
     * @param navigator goes back to home.
     */
    public void backToHome(final Navigator navigator) {
        navigator.changeView(Navigator.Screen.HOME);
    }

    /**
     * @param volume new volume
     */
    public void changeMusicVolume(final float volume) {
        model.setMusicVolume(volume);
    }

    /**
     * @param volume new volume
     */
    public void changeSoundVolume(final float volume) {
        model.setSoundVolume(volume);
    }

    /**
     * @param enabled true to enable music
     */
    public void toggleMusic(final boolean enabled) {
        model.setMusicEnabled(enabled);
    }

    /**
     * @param enabled true to enable sound effects
     */
    public void toggleSoundEffects(final boolean enabled) {
        model.setSoundEffectsEnabled(enabled);
    }
}

