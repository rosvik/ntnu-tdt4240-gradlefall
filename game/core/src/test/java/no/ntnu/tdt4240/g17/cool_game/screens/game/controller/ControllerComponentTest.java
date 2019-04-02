package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.libgdx.test.util.GameTest;
import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementFormat;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerComponentTest extends GameTest {
    ControllerComponent controllerComponent;

    @BeforeEach
    void setUp() {
        controllerComponent = ControllerComponent.getInstance();
    }

    @Test
    void getInstance() {
        Assert.assertThat(controllerComponent, Matchers.isA(ControllerComponent.class));
    }

    @Test
    void update() {
        System.out.println(controllerComponent.getMovementFormat());
        ControllerComponent.getInstance().update();
        float angle = controllerComponent.getMessage().moveAngle;
        float magnitude = controllerComponent.getMessage().moveSpeed;
        boolean jump = controllerComponent.getMessage().jump;
        boolean shoot = controllerComponent.getMessage().shoot;
        boolean block = controllerComponent.getMessage().placeBlock;
        Assert.assertThat(angle,Matchers.equalTo(0f));
        Assert.assertThat(magnitude, Matchers.equalTo(0f));
        Assert.assertFalse(jump);
        Assert.assertFalse(shoot);
        Assert.assertFalse(block);
    }
}