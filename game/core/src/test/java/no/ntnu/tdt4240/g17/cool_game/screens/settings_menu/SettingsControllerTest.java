package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

//TODO: skrive ferdig tester.
class SettingsControllerTest{

    private Navigator navigator;
    private SettingsController settingsController;
    private SettingsModel settingsModel;
    private Preferences preferences;

    @BeforeEach
    void setUp() {
        navigator = mock(Navigator.class);
        preferences = Mockito.mock(Preferences.class);
        settingsModel = Mockito.spy(new SettingsModel(preferences));

        settingsController = new SettingsController(settingsModel);
    }

    @Test
    void shouldUpdateModelWhenVolumeChanges() {
        // Given

        // When
        float volume = 0.3f;
        settingsController.changeMusicVolume(volume);

        // Then
        verify(settingsModel).setMusicVolume(volume);
    }

    @Test
    void shouldDisableSoundEffects() {
        // When
        settingsController.toggleSoundEffects(false);

        // Then
        verify(settingsModel).setSoundEffectsEnabled(false);
    }

    @Test
    void backToHome() {
        // When
        settingsController.backToHome(navigator);

        // Then
        verify(navigator).changeView(Navigator.Screen.HOME);
    }
}