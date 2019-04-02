package no.ntnu.tdt4240.g17.cool_game.projectile;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ProjectileStateTest {
    ProjectileState testState = new ProjectileState(50,50, 135, 24,24);

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
    void getDirectionAngle() {
        Assert.assertThat(testState.getDirectionAngle(), Matchers.isA(float.class));
    }

    @Test
    void setDirectionAngle() {
        testState.setDirectionAngle(10);
        Assert.assertTrue(145 == testState.getAngle());
        Assert.assertTrue(10 == testState.getDirectionAngle());
    }

    @Test
    void getDimension() {
        Assert.assertTrue(24 == testState.getWidth());
        Assert.assertTrue(24 == testState.getHeight());
    }
}