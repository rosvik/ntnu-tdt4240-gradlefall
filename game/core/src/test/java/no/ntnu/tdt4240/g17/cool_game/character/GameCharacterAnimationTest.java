package no.ntnu.tdt4240.g17.cool_game.character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.libgdx.test.util.GameTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class GameCharacterAnimationTest extends GameTest {
    public TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("DungeonTilset.atlas"));
    public String name  = "wizzard_m";
    public GameCharacterAnimation testAnimation = new GameCharacterAnimation("wizzard_m", atlas);
    public final float FRAME_DURATION = 1/8f;


    @Test
    void jump() {
        this.testAnimation.jump();
        Assert.assertEquals(this.testAnimation.getJumpingAnimation(), testAnimation.getAnimation());
    }

    @Test
    void fall() {
        this.testAnimation.fall();
        Assert.assertEquals(this.testAnimation.getFallingAnimation(), testAnimation.getAnimation());
    }

    @Test
    void moveLeft() {
        this.testAnimation.moveLeft();
        Assert.assertEquals(this.testAnimation.getRunningAnimation(), testAnimation.getAnimation());
        Assert.assertTrue(this.testAnimation.isMovingLeft());
    }

    @Test
    void moveRigth() {
        this.testAnimation.moveRigth();
        Assert.assertEquals(this.testAnimation.getRunningAnimation(), testAnimation.getAnimation());
        Assert.assertFalse(this.testAnimation.isMovingLeft());
    }

    @Test
    void idle() {
        this.testAnimation.idle();
        Assert.assertEquals(this.testAnimation.getIdleAnimation(), testAnimation.getAnimation());
    }

    @Test
    void hit() {
        this.testAnimation.hit();
        Assert.assertEquals(this.testAnimation.getFallingAnimation(), testAnimation.getAnimation());
    }

    @Test
    void getHeight() {
        Assert.assertTrue(this.testAnimation.getFrame(0).getRegionHeight() == this.testAnimation.getHeight());
    }

    @Test
    void getWidth() {
        Assert.assertTrue(20 == this.testAnimation.getWidth());
    }
}