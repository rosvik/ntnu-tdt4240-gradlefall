package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GameCharacterTest  extends GameTest {
    private TextureAtlas projectiles = new TextureAtlas("./TextureAtlas/Projectiles/Projectiles.atlas");
    private GameCharacter gameCharacter = new GameCharacter(
            "wizzard_m",
            100,
            100,
            new TextureAtlas("TextureAtlas/Characters/DungeonTileset.atlas"),
            "arrow",
            projectiles);

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