package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.MovementFormat;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.UserInputButtons;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInputButtonsTest {

    UserInputButtons UIButtons;
    int SCREEN_HEIGHT = 576;
    int SCREEN_WIDTH = 1024;

    @Test
    void shouldBe90DegreesWhenStraightUp() {
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(160, 310);
        // Then
        assertEquals(movementFormat.getButtonInput(), "joystick");
        assertEquals(movementFormat.getJoystickInput(), new Vector2(90, 100));
    }

    @Test
    void shouldBe270DegreesWhenStraightDown(){
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(160, 522);
        // Then
        assertEquals(movementFormat.getButtonInput(), "joystick");
        assertEquals(movementFormat.getJoystickInput(), new Vector2(270, 100));
    }

    @Test
    void shouldBe0DegreesWhenStraightRight(){
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(266, 416);
        // Then
        assertEquals(movementFormat.getButtonInput(), "joystick");
        assertEquals(movementFormat.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBe180DegreesWhenStraightLeft(){
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(54, 416);
        // Then
        assertEquals(movementFormat.getButtonInput(), "joystick");
        assertEquals(movementFormat.getJoystickInput(), new Vector2(180, 100));
    }

    @Test
    void magnitudeShouldNotBeGreaterThan106(){
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(50, 416);
        // Then
        assertEquals(movementFormat.getJoystickInput(), new Vector2(180, 100));
    }

    @Test
    void shouldOutputJump(){
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(766, 425);
        // Then
        assertEquals(movementFormat.getButtonInput(), "jump");
        assertEquals(movementFormat.getJoystickInput(), new Vector2(0, 0));
    }

    @Test
    void shouldOutputShoot(){
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(764, 525);
        // Then
        assertEquals(movementFormat.getButtonInput(), "shoot");
        assertEquals(movementFormat.getJoystickInput(), new Vector2(0, 0));
    }

    @Test
    void shouldOutputPlace(){
        // Given
        UIButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        // When
        MovementFormat movementFormat = UIButtons.processInput(944, 520);
        // Then
        assertEquals(movementFormat.getButtonInput(), "place");
        assertEquals(movementFormat.getJoystickInput(), new Vector2(0, 0));
    }

}