package no.ntnu.tdt4240.g17.cool_game.powerup;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PowerupTest extends GameTest {
    TextureAtlas atlas = new TextureAtlas("TextureAtlas/Characters/DungeonTileset.atlas");
    Powerup testPowerup = new Powerup("chest_empty_open_anim", 0, 0, atlas);

    @Test
    void getState() {
        Assert.assertThat(testPowerup.getState(), Matchers.isA(PowerupState.class));
    }

    @Test
    void getScale() {
        Assert.assertTrue(1 == testPowerup.getScale(24f));
    }

    @Test
    void render() {
        float x = testPowerup.getState().getxPosition();
        float y = testPowerup.getState().getyPosition();

        Assert.assertTrue(x == testPowerup.getState().getxPosition());
        Assert.assertTrue(y == testPowerup.getState().getyPosition());

        testPowerup.render();
        Assert.assertTrue(x == testPowerup.getState().getxPosition());
        Assert.assertFalse(y == testPowerup.getState().getyPosition());

        for (int i = 0; i < 30; i++) {
            testPowerup.render();
        }
        System.out.println(new Float(testPowerup.getState().getyPosition()));
        System.out.println(y + 9*0.007f + 0.00000001);
        Assert.assertTrue((0.64) > testPowerup.getState().getyPosition());

    }

    @Test
    void draw() {
    }
}