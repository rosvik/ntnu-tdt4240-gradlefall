package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {
    GameView view;

    @BeforeEach
    void setUp() {
        view = new GameView();
        view.batch = Mockito.mock(SpriteBatch.class);
    }

    @Test
    void show(){
        view.show();
    }
}