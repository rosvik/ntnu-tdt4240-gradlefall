package no.ntnu.tdt4240.g17.cool_game.screens.main_menu;

import com.badlogic.gdx.Gdx;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * used to control homescreen/view.
 */
public class HomeController {

    private Navigator parent;

    /**
     * Create a new HomeController for a {@link HomeView}.
     * @param navigator navigator class
     */
    public HomeController(final Navigator navigator) {
        this.parent = navigator;
    }

    /**
     * exits the program.
     */
    public void quit() {
        Gdx.app.exit();
    }

    /**
     * g.
     */
    public void changeToSettings() {
        parent.changeView(Navigator.Screen.SETTING);
    }
}
