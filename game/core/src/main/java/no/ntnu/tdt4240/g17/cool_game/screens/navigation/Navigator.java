package no.ntnu.tdt4240.g17.cool_game.screens.navigation;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;


import lombok.Data;
import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeController;
import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeView;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsController;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsModel;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsView;

/**
 *
 */
@Data
public class Navigator implements Disposable {

    /**
     * A screen in the application.
     */
    public enum Screen {
        /**
         * Main home screen.
         */
        HOME,
        /**
         * Settings screen.
         */
        SETTING
    }

    /**
     * Home to refer to be used later on.
     */
    public static final int HOME = 0;
    /**
     * Settings refers to static variable to be used later on.
     */
    public static final int SETTING = 1;

    private com.badlogic.gdx.Screen screen;


    /**
     * initialzed throught a constructor.
     */
    public Navigator() {
        this.initialize();
    }

    /**
     * Initialize the navigator.
     * TODO: lag en factory for alle views
     */
    public void initialize() {
        HomeView homeView = new HomeView(new HomeController(this));
        setScreen(homeView);
    }

    /**
     * @param screenIndex is screenIndex. method changes view.
     */
    public void changeView(final Screen screenIndex) {

        switch (screenIndex) {
            case SETTING:
                SettingsModel settingsModel = new SettingsModel(Gdx.app.getPreferences(SettingsModel.PREFS_NAME));
                SettingsView settingsView = new SettingsView(this,
                        new SettingsController(settingsModel), settingsModel);
                this.setScreen(settingsView);
                break;
            default:
            case HOME:
                HomeView homeView = new HomeView(new HomeController(this));
                this.setScreen(homeView);
                break;
        }
    }

    /**
     * Set the currently active screen.
     *
     * @param screen .
     */
    public void setScreen(final com.badlogic.gdx.Screen screen) {
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
    public com.badlogic.gdx.Screen getScreen() {
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


