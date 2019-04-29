package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.badlogic.gdx.math.Vector2;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ControllerServerComponentTest {
    ControllerServerComponent csc = new ControllerServerComponent();
    ArrayList buttonPressed = new ArrayList();
    Vector2 joystick = new Vector2(0,0);
    MovementFormat mf =  new MovementFormat(buttonPressed, joystick);
    float prevX;
    float prevY;

    @BeforeEach
    void setUp() {
        buttonPressed.add(0);
        buttonPressed.add(0);
        buttonPressed.add(0);
        buttonPressed.add(0);
    }

    @Test
    void movingDown() {
        prevX = csc.getX();
        prevY = csc.getY();
        joystick.x = 260;
        joystick.y = 100;
        buttonPressed.set(1,1);
        csc.update(mf);
        Assert.assertThat(csc.getY(), Matchers.lessThan(prevY));
    }

    @Test
    void movingUp() {
        prevX = csc.getX();
        prevY = csc.getY();
        joystick.x = 90;
        joystick.y = 50;
        buttonPressed.set(1, 1);
        csc.update(mf);
        Assert.assertThat(csc.getY(), Matchers.greaterThan(prevY));
    }

    @Test
    void movingRigth() {
        prevX = csc.getX();
        prevY = csc.getY();
        joystick.x = 60;
        joystick.y = 50;
        csc.update(mf);
        Assert.assertThat(csc.getX(), Matchers.greaterThan(prevX));
    }

    @Test
    void movingLeft() {
        prevX = csc.getX();
        prevY = csc.getY();
        joystick.x = 180;
        joystick.y = 50;
        csc.update(mf);
        Assert.assertThat(csc.getX(), Matchers.lessThan(prevX));
    }



    @Test
    void getX() {
        Assert.assertThat(csc.getX(), Matchers.equalTo(10f));
    }

    @Test
    void getY() {
        Assert.assertThat(csc.getY(), Matchers.equalTo(10f));

    }

    @Test
    void shootPressed() {
        Assert.assertThat(csc.shootPressed(), Matchers.equalTo(false));
        buttonPressed.set(2,1);
        csc.update(mf);
        Assert.assertThat(csc.shootPressed(), Matchers.equalTo(true));
    }

    @Test
    void getAngle() {
        Assert.assertThat(csc.getAngle(), Matchers.equalTo(0f));
        joystick.y = 90;
        csc.update(mf);
        Assert.assertThat(csc.getMagnitude(), Matchers.equalTo(90f));
    }

    @Test
    void getMagnitude() {
        Assert.assertThat(csc.getMagnitude(), Matchers.equalTo(0f));
        joystick.y = 100;
        csc.update(mf);
        Assert.assertThat(csc.getMagnitude(), Matchers.equalTo(100f));
    }
}