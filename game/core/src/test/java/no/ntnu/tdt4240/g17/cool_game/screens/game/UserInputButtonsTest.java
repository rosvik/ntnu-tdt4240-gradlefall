package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInputButtonsTest {

    UserInputButtons UIButtons;
    int SCREEN_HEIGHT = 576;
    int SCREEN_WIDTH = 1024;

    @BeforeEach
    void setUp() {
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
    }

    @Test
    void shouldBe90DegreesWhenStraightUp() {
        MovementOutput movementOutput = UIButtons.processInput(160, 310);
        assertEquals(movementOutput.getButtonOutput(), "joystick");
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(90, 100));
    }

    @Test
    void shouldBe270DegreesWhenStraightDown(){
        MovementOutput movementOutput = UIButtons.processInput(160, 522);
        assertEquals(movementOutput.getButtonOutput(), "joystick");
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(270, 100));
    }

    @Test
    void shouldBe0DegreesWhenStraightRight(){
        MovementOutput movementOutput = UIButtons.processInput(266, 416);
        assertEquals(movementOutput.getButtonOutput(), "joystick");
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(0, 100));
    }

    @Test
    void shouldBe180DegreesWhenStraightLeft(){
        MovementOutput movementOutput = UIButtons.processInput(54, 416);
        assertEquals(movementOutput.getButtonOutput(), "joystick");
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(180, 100));
    }

    @Test
    void magnitudeShouldNotBeGreaterThan106(){
        MovementOutput movementOutput = UIButtons.processInput(50, 416);
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(180, 100));
    }

    @Test
    void shouldOutputJump(){
        MovementOutput movementOutput = UIButtons.processInput(766, 425);
        assertEquals(movementOutput.getButtonOutput(), "jump");
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(0, 0));
    }

    @Test
    void shouldOutputShoot(){
        MovementOutput movementOutput = UIButtons.processInput(764, 525);
        assertEquals(movementOutput.getButtonOutput(), "shoot");
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(0, 0));
    }

    @Test
    void shouldOutputPlace(){
        MovementOutput movementOutput = UIButtons.processInput(944, 520);
        assertEquals(movementOutput.getButtonOutput(), "place");
        assertEquals(movementOutput.getJoystickOutput(), new Vector2(0, 0));
    }

}