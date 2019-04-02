package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HeadsUpDisplayTest extends GameTest {
    TextureAtlas tileset1 = new TextureAtlas(Gdx.files.internal("./TextureAtlas/Characters/DungeonTileset.atlas"));
    TextureAtlas tileset2 = new TextureAtlas(Gdx.files.internal("./TextureAtlas/Projectiles/Projectiles.atlas"));
    SpriteBatch batch;
    HeadsUpDisplay hud = new HeadsUpDisplay(tileset1, tileset2);

    @BeforeEach
    void setUp() {
        batch = Mockito.mock(SpriteBatch.class);
        Mockito.when(batch.getColor()).thenReturn(Color.BLACK);
    }

    @Test
    void draw(){
        hud.draw(batch,3,3,0);
    }
}