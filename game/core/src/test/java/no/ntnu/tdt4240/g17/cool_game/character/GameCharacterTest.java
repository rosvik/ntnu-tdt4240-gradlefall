package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCharacterTest  extends GameTest {

    private GameCharacter gameCharacter = new GameCharacter("wizzard_m", 100, 100, new TextureAtlas(Gdx.files.internal("DungeonTilset.atlas")));

    @Test
    void render() {
        gameCharacter.render(101,100);
        Assert.assertFalse(gameCharacter.getAnimation().isMovingLeft());
        gameCharacter.render(99,100);
        Assert.assertTrue(gameCharacter.getAnimation().isMovingLeft());
        gameCharacter.render(99,101);
        Assert.assertEquals(gameCharacter.getAnimation().getJumpingAnimation(), gameCharacter.getAnimation().getAnimation());
        gameCharacter.render(99,99);
        Assert.assertEquals(gameCharacter.getAnimation().getFallingAnimation(), gameCharacter.getAnimation().getAnimation());
    }
}