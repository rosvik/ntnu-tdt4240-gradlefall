package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;


class GameCharacterStateTest extends GameTest {
    private TextureAtlas projectiles = new TextureAtlas("./TextureAtlas/Projectiles/Projectiles.atlas");
    private GameCharacterState testState = new GameCharacterState(0,0, "arrow", projectiles);

    @Test
    void testGetyPosistion() {
        Assert.assertTrue(0  == this.testState.getyPosition());
        //A public void method annotated with @Test will be executed as a test case.
    }

    @Test
    void testGetxPosistion() {
        Assert.assertTrue(0 == this.testState.getxPosition());
        //A public void method annotated with @Test will be executed as a test case.
    }

    @Test
    void testSetters() {
        this.testState.setxPosition(10);
        this.testState.setyPosition(10);
        Assert.assertTrue(10 == this.testState.getxPosition());
        Assert.assertTrue(10 == this.testState.getyPosition());

        this.testState.setPosition(50,50);
        Assert.assertTrue(50 == this.testState.getxPosition());
        Assert.assertTrue(50 == this.testState.getyPosition());
    }

    @Test
    void testGetLives(){
        Assert.assertEquals(3, this.testState.getLives());
    }

    @Test
    void testRemoveLives(){
        this.testState.removeLives(1);
        Assert.assertEquals(2, this.testState.getLives());
    }

    @Test
    void testAddLives(){
        this.testState.addLives(1);
        Assert.assertEquals(4,this.testState.getLives());
    }

    @Test
    void testIsDead(){
        Assert.assertFalse(this.testState.isDead());
    }

    @Test
    void testGetScore(){
        Assert.assertEquals(0, this.testState.getScore());
    }

    @Test
    void testIncreaseScore(){
        this.testState.increaseScore(100);
        Assert.assertEquals(100, this.testState.getScore());
    }

    @Test
    void testProjectiles(){
        Assert.assertEquals(3,this.testState.getNumberOfProjectiles());
        this.testState.shoot();
        Assert.assertEquals(2,this.testState.getNumberOfProjectiles());
        this.testState.addProjectiles(2);
        Assert.assertEquals(4,this.testState.getNumberOfProjectiles());
    }
}