package no.ntnu.tdt4240.g17.cool_game.screens.navigation;

import com.badlogic.gdx.Game;


import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeController;
import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeView;
import no.ntnu.tdt4240.g17.cool_game.screens.loading.LoadingView;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsController;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsView;

/**
 *
 */

public class Navigator extends Game {
    /**
     * Home to refer to be used later on.
     */
    public static final int HOME = 0;
    /**
     * Settings refers to static variable to be used later on.
     */
    public static final int SETTING = 1;

    /**
     * storingvariable for loadingView.
     */
    private LoadingView loadingView;
    /**
     * storingvariable.
     */
    private SettingsView settingsView;
    /**
     * storingvariable.
     */
    private HomeView homeView;

    private SettingsController preferences;

    /**
     * defaultscreen when started.
     */
    @Override
    public void create() {
        loadingView = new LoadingView(this);
        preferences = new SettingsController();
        setScreen(loadingView);

    }

    /**
     * @param screen is screen. method changes view.
     */
    public void changeView(final int screen) {

        switch (screen) {
            case SETTING:
                settingsView = new SettingsView(this, new SettingsController());
                this.setScreen(settingsView);
                break;
            default:
            case HOME:
                homeView = new HomeView(this, new HomeController(this));
                this.setScreen(homeView);
                break;
        }
    }

    /**
     * @return preferences
     */
    public SettingsController getPreferences() {
        return this.preferences;
    }
}


