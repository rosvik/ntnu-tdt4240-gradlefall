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
     *
     * @param navigator navigator class
     */
    public HomeController(final Navigator navigator) {
        this.parent = navigator;
    }

    /**
     * starts music.
     */
    /*public void startMusic() {
        Music music;
        music = Gdx.audio.newMusic(Gdx.files.internal("Tune1.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }*/

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
