package no.ntnu.tdt4240.g17.cool_game.screens.main_menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsController;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsView;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HomeControllerTest {


    private Navigator navigator;
    private HomeController homeController;

    @BeforeEach
    void setUp(){
        navigator = mock(Navigator.class);
        homeController = new HomeController(navigator);
    }

    @Test
    void shouldCallGdxQuit() {
        // When
        Gdx.app = mock(Application.class);

        // When
        homeController.quit();
        // Then
        verify(Gdx.app).exit();
    }


    @Test
    void changeToSettings() {
        // When
        homeController.changeToSettings();

        // Then
        verify(navigator).changeView(Navigator.Screen.SETTING);
    }
}