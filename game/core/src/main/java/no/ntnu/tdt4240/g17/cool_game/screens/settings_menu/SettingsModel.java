package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Preferences;

/**
 * Model for settings view.
 */
public class SettingsModel {

    /**
     * Preference name.
     */
    public static final String PREFS_NAME = "navigator";

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";

    private final Preferences prefs;

    /**
     * Create a new model.
     * @param prefs the preferences to use.
     * {@link com.badlogic.gdx.Gdx#app Gdx.app}
     * {@link com.badlogic.gdx.Application#getPreferences(String) .getPreferences}.
     */
    public SettingsModel(final Preferences prefs) {
        this.prefs = prefs;
    }

    /**
     * @return volum
     */
    public float getMusicVolume() {
        final float defaultVolume = 0.5f;
        return prefs.getFloat(PREF_MUSIC_VOLUME, defaultVolume);
    }

    /**
     * @param volume volum.
     */
    public void setMusicVolume(final float volume) {
        prefs.putFloat(PREF_MUSIC_VOLUME, volume);
        //makes sure it is written to disk and saved
        prefs.flush();
    }

    /**
     * @return boolean value.
     */
    public boolean isSoundEffectsEnabled() {
        return prefs.getBoolean(PREF_SOUND_ENABLED, true);
        //return true;
    }

    /** method checks coundeffect checkbox and saves state.
     * @param soundEffectsEnabled boolean for if its enabled or not.
     */
    public void setSoundEffectsEnabled(final boolean soundEffectsEnabled) {
        prefs.putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        prefs.flush();
    }

    /**
     * @return boolean for music.
     */
    public boolean isMusicEnabled() {
        return prefs.getBoolean(PREF_MUSIC_ENABLED, true);
    }

    /**
     * @param musicEnabled is enabled.
     */
    public void setMusicEnabled(final boolean musicEnabled) {
        prefs.putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        prefs.flush();
    }

    /**
     * @return sound voleume.
     */
    public float getSoundVolume() {
        final float defaultVolume = 0.5f;
        return prefs.getFloat(PREF_SOUND_VOL, defaultVolume);
    }

    /**
     * @param volume volume.
     */
    public void setSoundVolume(final float volume) {
        prefs.putFloat(PREF_SOUND_VOL, volume);
        prefs.flush();
    }

}
