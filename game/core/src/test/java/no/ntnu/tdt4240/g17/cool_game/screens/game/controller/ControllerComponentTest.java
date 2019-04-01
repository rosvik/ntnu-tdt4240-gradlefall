package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.libgdx.test.util.GameTest;
import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementFormat;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerComponentTest extends GameTest {
    ControllerComponent controllerComponent = ControllerComponent.getInstance();

    @Test
    void getInstance() {
        Assert.assertThat(controllerComponent, Matchers.isA(ControllerComponent.class));
    }

    @Test
    void update() {
        controllerComponent.update();
        float angle = controllerComponent.getMovementFormat().getJoystickInput().x;
        float magnitude = controllerComponent.getMovementFormat().getJoystickInput().y;
        ArrayList buttons = controllerComponent.getMovementFormat().getButtonsPressed();
        Assert.assertThat(angle,Matchers.equalTo(0f));
        Assert.assertThat(magnitude, Matchers.equalTo(0f));
        Assert.assertThat(buttons.get(0), Matchers.equalTo(0));
        Assert.assertThat(buttons.get(1), Matchers.equalTo(0));
        Assert.assertThat(buttons.get(2), Matchers.equalTo(0));
        Assert.assertThat(buttons.get(3), Matchers.equalTo(0));
    }

    @Test
    void getMovementFormat() {
        Assert.assertThat(controllerComponent.getMovementFormat(), Matchers.isA(MovementFormat.class));
    }
}