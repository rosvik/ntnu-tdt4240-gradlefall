package no.ntnu.tdt4240.g17.cool_game.character;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameCharacterAnimationTest {
    private TextureAtlas atlas;
    public String name;
    public GameCharacterAnimation testAnimation;
    public final float FRAME_DURATION = 1/8f;

    @Before
    public  void setUp() throws Exception {
        this.atlas = new TextureAtlas("DungeonTilset.png\n" +
                "size: 1734, 64\n" +
                "format: RGBA8888\n" +
                "filter: Linear,Linear\n" +
                "repeat: none\n" +
                "big_demon_idle_anim\n" +
                "  rotate: false\n" +
                "  xy: 249, 1\n" +
                "  size: 25, 31\n" +
                "  orig: 32, 36\n" +
                "  offset: 4, 0\n" +
                "  index: -1");
        this.name = "wizzard_m";
        this.testAnimation = new GameCharacterAnimation(name,  new TextureAtlas());
    }


    @Test
    void jump() {
        System.out.println("TEST");
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString()+"";
        System.out.println("jump: " + this.atlas);
        Assert.assertEquals(new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegion(name + "_idle_anim")), this.testAnimation.getAnimation());
    }

    @Test
    void fall() {
    }

    @Test
    void moveLeft() {
    }

    @Test
    void moveRigth() {
    }

    @Test
    void idle() {
    }

    @Test
    void hit() {
    }

    @Test
    void getHeight() {
    }

    @Test
    void getWidth() {
    }

    @Test
    void getFrame() {
    }
}