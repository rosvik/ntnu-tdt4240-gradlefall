package no.ntnu.tdt4240.g17.cool_game.screens.navigation;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeView;

import static org.mockito.Mockito.mock;
@Disabled("Navigator is not testable because of Stage needing a SpriteBatch.")
class NavigatorTest {

    private Navigator navigator;

    //Navigator navigator;
    @BeforeEach
    void setUp() {
        navigator = new Navigator();
        //homeView = new HomeView(navigator, new HomeController(navigator));
    }


    @Test
    void initialize() {
    }

    //TODO: fix i navigator. flytt
    @Test
    void changeView() {
        Gdx.app = mock(Application.class);

        // When
        navigator.changeView(Navigator.Screen.HOME);

        // THen
        Mockito.verify(navigator).setScreen(Mockito.any(HomeView.class));
    }


    @Test
    void dispose() {
    }
}