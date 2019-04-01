package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.test.util.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameViewTest extends GameTest {
    GameView view;

    @BeforeEach
    void setUp() {
        view = new GameView(1, Mockito.mock(SpriteBatch.class));
        view.dungeonTileset = Gdx.files.internal("./TextureAtlas/Characters/DungeonTileset.atlas").toString();
        view.projectileTileset = Gdx.files.internal("./TextureAtlas/Projectiles/Projectiles.atlas").toString();
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
}