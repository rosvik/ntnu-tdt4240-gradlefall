package no.ntnu.tdt4240.g17.cool_game.screens.navigation;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;


import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeController;
import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeView;
import no.ntnu.tdt4240.g17.cool_game.screens.loading.LoadingView;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsController;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsView;

/**
 *
 */

public class Navigator implements Disposable {
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

    private Screen screen;

    /**
     * Initialize the navigator.
     */
    public void initialize() {
        loadingView = new LoadingView(this);
        preferences = new SettingsController();
        homeView = new HomeView(this, new HomeController(this));
        setScreen(homeView);
    }


    /**
     * @param screenIndex is screenIndex. method changes view.
     */
    public void changeView(final int screenIndex) {

        switch (screenIndex) {
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

    /**
     * Set the currently active screen.
     * @param screen .
     */
    public void setScreen(final Screen screen) {
        if (this.screen != null) {
            this.screen.hide();
            this.screen.dispose();
        }
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
        }
    }

    /**
     * @return the currently actvive screen or null.
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * dispoces method to implemented from the interface.
     */
    @Override
    public void dispose() {
        this.screen.dispose();
        this.screen = null;
    }
}


