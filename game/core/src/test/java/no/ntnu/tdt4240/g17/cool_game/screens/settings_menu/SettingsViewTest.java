package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

class SettingsViewTest extends GameTest {

    private SettingsView settingsView;
    private Navigator navigator;
    private Preferences preferences;
    private SettingsModel settingsModel;
    private SettingsController settingsController;

    @BeforeEach
    void setUp() {
        navigator = Mockito.mock(Navigator.class);
        preferences = Mockito.mock(Preferences.class);
        settingsModel = new SettingsModel(preferences);
        settingsController = Mockito.spy(new SettingsController(settingsModel));
        settingsView = new SettingsView(navigator, settingsController, settingsModel);
    }


}