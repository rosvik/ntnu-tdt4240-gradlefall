package no.ntnu.tdt4240.g17.cool_game.screens.loading;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/30/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class LoadingController {

    private final Navigator navigator;

    /** @param navigator used to navigate to other screens. */
    public LoadingController(final Navigator navigator) {
        this.navigator = navigator;
    }

    /** Call when the game should start. */
    public void startGame() {
        navigator.changeView(Navigator.Screen.GAME);
    }
}
