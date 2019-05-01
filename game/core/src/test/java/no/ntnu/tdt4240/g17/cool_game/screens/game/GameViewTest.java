package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.test.util.GameTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

@Disabled("Can't run because asset loading fails it")
class GameViewTest extends GameTest {
    GameView view;

    @BeforeEach
    void setUp() {
        view = new GameView(Mockito.mock(SpriteBatch.class), Mockito.mock(Navigator.class));
        view.show();
    }

    @Test
    void shouldRender() {
        view.render(1f);
    }

    @Test
    void resume(){
        view.resume();
    }

    @Test
    void pause(){
        view.pause();
    }

    @Test
    void resize(){
        view.resize(0,0);
    }

    @Test
    void dispose(){
        view.dispose();
    }

    @Test
    void hide(){
        view.hide();
    }
}