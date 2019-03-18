package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.InstanceOf;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementOutputTest {

    MovementOutput movementOutput


    int SCREEN_HEIGHT = 576;
    int SCREEN_WIDTH = 1024;

    @BeforeEach
    void setUp() {
        movementOutput = new MovementOutput("jump", new Vector2(0,0));
    }

    @Test
    void buttonOutputShouldBeString() {
       // assertTrue(movementOutput.getButtonOutput(), InstanceOf(String));
       // assertTrue(movementOutput.getButtonOutput().)
    }

    @Test
  //  void shouldBe270DegreesWhenStraightDown(){
    //    MovementOutput movementOutput = UIButtons.processInput(160, 522);
      //  assertEquals(movementOutput.getButtonOutput(), "joystick");
        //assertEquals(movementOutput.getJoystickOutput(), new Vector2(270, 100));
    //}

}