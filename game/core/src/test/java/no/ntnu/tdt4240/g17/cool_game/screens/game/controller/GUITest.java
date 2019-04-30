package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.libgdx.test.util.GameTest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GUITest extends GameTest {
    GUI GUI;

    @BeforeEach
    void setUp() {
        GUI = no.ntnu.tdt4240.g17.cool_game.screens.game.controller.GUI.getInstance();
    }

    @Test
    void getInstance() {
        Assert.assertThat(GUI, Matchers.isA(GUI.class));
    }

    @Test
    void update() {
        System.out.println(GUI.getMovementFormat());
        no.ntnu.tdt4240.g17.cool_game.screens.game.controller.GUI.getInstance().update();
        float angle = GUI.getMessage().moveAngle;
        float magnitude = GUI.getMessage().moveSpeed;
        boolean jump = GUI.getMessage().jump;
        boolean shoot = GUI.getMessage().shoot;
        boolean block = GUI.getMessage().placeBlock;
        Assert.assertThat(angle,Matchers.equalTo(0f));
        Assert.assertThat(magnitude, Matchers.equalTo(0f));
        Assert.assertFalse(jump);
        Assert.assertFalse(shoot);
        Assert.assertFalse(block);
    }
}