package no.ntnu.tdt4240.g17.cool_game.screens.navigation;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.screens.game.GameView;
import no.ntnu.tdt4240.g17.cool_game.screens.loading.LoadingController;
import no.ntnu.tdt4240.g17.cool_game.screens.loading.LoadingModel;
import no.ntnu.tdt4240.g17.cool_game.screens.loading.LoadingView;
import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeController;
import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeModel;
import no.ntnu.tdt4240.g17.cool_game.screens.main_menu.HomeView;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsController;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsModel;
import no.ntnu.tdt4240.g17.cool_game.screens.settings_menu.SettingsView;

/**
 * Navigates between screens in the application.
 */
@Slf4j
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
        SETTING,
        /** Matchmaking screen. */
        MATCHMAKING,
        /**
         * Game screen.
         */
        GAME
    }

    private com.badlogic.gdx.Screen screen;


    /**
     * initialzed throught a constructor.
     */
    public Navigator() {
        changeView(Screen.HOME);
    }

    /**
     * @param screenType is screenType. method changes view.
     */
    public void changeView(final Screen screenType) {
        log.info("Changing screen to: {}", screenType.name());

        switch (screenType) {
            case SETTING:
                SettingsModel settingsModel = new SettingsModel(Gdx.app.getPreferences(SettingsModel.PREFS_NAME));
                SettingsView settingsView = new SettingsView(this,
                        new SettingsController(settingsModel), settingsModel);
                this.setScreen(settingsView);
                break;

            case GAME:
                GameView gameView = new GameView(new SpriteBatch());
                this.setScreen(gameView);
                break;

            case MATCHMAKING:
                final LoadingModel loadingModel = new LoadingModel(GameClient.getNetworkClientInstance());
                loadingModel.setOnMatchmadeListener(ClientData.getInstance()::receive);
                LoadingView loadingView = new LoadingView(new SpriteBatch(),
                        loadingModel, new LoadingController(this, loadingModel));
                this.setScreen(loadingView);
                break;

            default:
            case HOME:
                HomeView homeView = new HomeView(new HomeController(this), new SpriteBatch(), new HomeModel());
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
        if (this.screen != null) {
            this.screen.dispose();
        }
        this.screen = null;
    }
}


