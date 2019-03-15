package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;


/**
 * Controller for settingsscreen.
 */
public class SettingsController {

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "mainClass";

    /**
     * magic number.
     */
    public static final float HALFFRACTION = 0.5f;

    /**
     * @return name
     */
    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    /**
     * @return volum
     */
    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, HALFFRACTION);
    }

    /**
     * @param volume volum.
     */
    public void setMusicVolume(final float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        //makes sure it is written to disk and saved
        getPrefs().flush();
    }

    /**
     * @return boolean value.
     */
    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    /**
     * @param soundEffectsEnabled boolean for if its enabled or not.
     */
    public void setSoundEffectsEnabled(final boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    /**
     * @return boolean for music.
     */
    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    /**
     * @param musicEnabled is enabled.
     */
    public void setMusicEnabled(final boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    /**
     * @return sound voleume.
     */
    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, HALFFRACTION);
    }

    /**
     * @param volume volume.
     */
    public void setSoundVolume(final float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    /**
     * adjusting the volume.
     */
    public void volumSlide() {
    }


    /**
     * @param navigator goes back to home.
     */
    public void backToHome(final Navigator navigator) {
        navigator.changeView(navigator.HOME);
    }


    private Skin skin = new Skin(Gdx.files.internal("android/assets/skin/neon-ui.json"));

    /**
     * @param navigator takes mainclass as param.
     *                  Bundet til metoden handle i Settingsview.
     */
    public void getMainPrefs(final Navigator navigator) {
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        boolean enabled = soundEffectsCheckbox.isChecked();
        navigator.getPreferences().setSoundEffectsEnabled(enabled);
    }
}
