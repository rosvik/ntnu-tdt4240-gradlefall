package no.ntnu.tdt4240.g17.cool_game.powerup;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PowerupStateTest {
    PowerupState testState = new PowerupState(50,50, 24, 24);

    @Test
    void getxPosition() {
        Assert.assertTrue(50 == testState.getxPosition());
    }

    @Test
    void setxPosition() {
        testState.setxPosition(99);
        Assert.assertTrue(99 == testState.getxPosition());
    }

    @Test
    void getyPosition() {
        Assert.assertTrue(50 == testState.getyPosition());
    }

    @Test
    void setyPosition() {
        testState.setyPosition(99);
        Assert.assertTrue(99 == testState.getyPosition());
    }

    @Test
    void getDimension() {
        Assert.assertTrue(24 == testState.getWidth());
        Assert.assertTrue(24 == testState.getHeight());
    }
}
