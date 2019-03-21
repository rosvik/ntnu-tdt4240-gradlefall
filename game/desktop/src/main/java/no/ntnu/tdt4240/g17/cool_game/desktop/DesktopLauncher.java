package no.ntnu.tdt4240.g17.cool_game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import no.ntnu.tdt4240.g17.cool_game.MainGame;

/**
 * Starts the desktop version of the game.
 */
public final class DesktopLauncher {

    private static final int SCREEN_WIDTH = 1024;
    private static final int SCREEN_HEIGHT = 576;

    /** Hidden constructor. */
    private DesktopLauncher() { }

    /** Main method for desktop.
     * @param arg command line arguments
     */
    public static void main(final String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = SCREEN_WIDTH;
        config.height = SCREEN_HEIGHT;
        new LwjglApplication(new MainGame(), config);
    }
}
