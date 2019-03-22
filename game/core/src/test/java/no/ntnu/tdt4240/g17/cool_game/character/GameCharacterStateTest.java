package no.ntnu.tdt4240.g17.cool_game.character;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class GameCharacterStateTest {
    private GameCharacterState testState = new GameCharacterState(0,0);


    @Test
    public void testGetyPosistion() {
        Assert.assertEquals(0,this.testState.getyPosition());
        //A public void method annotated with @Test will be executed as a test case.
    }

    @Test
    public void testGetxPosistion() {
        Assert.assertEquals(0,this.testState.getxPosition());
        //A public void method annotated with @Test will be executed as a test case.
    }

    @Test
    public void testSetters() {
        this.testState.setxPosition(10);
        this.testState.setyPosition(10);
        Assert.assertEquals(10,this.testState.getxPosition());
        Assert.assertEquals(10,this.testState.getyPosition());

        this.testState.setPosition(50,50);
        Assert.assertEquals(50,this.testState.getxPosition());
        Assert.assertEquals(50,this.testState.getyPosition());
    }

    @Test
    public void testGetLives(){
        Assert.assertEquals(3, this.testState.getLives());
    }

    @Test
    public void testRemoveLives(){
        this.testState.removeLives(1);
        Assert.assertEquals(2, this.testState.getLives());
    }

    @Test
    public void testAddLives(){
        this.testState.addLives(1);
        Assert.assertEquals(4,this.testState.getLives());
    }

    @Test
    public void testIsDead(){
        Assert.assertFalse(this.testState.isDead());
    }

    @Test
    public void testGetScore(){
        Assert.assertEquals(0, this.testState.getScore());
    }

    @Test
    public void testIncreaseScore(){
        this.testState.increaseScore(100);
        Assert.assertEquals(100, this.testState.getScore());
    }
}