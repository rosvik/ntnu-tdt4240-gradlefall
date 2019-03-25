package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementFormat;
import no.ntnu.tdt4240.g17.cool_game.screens.game.UserInputButtons;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputToMovementOutputTest {

    static InputToMovementOutput inputToMovementOutput;
    static UserInputButtons userInputButtons;
    static MovementFormat joystickFinger, jumpFinger, shootFinger, placeFinger;
    static ArrayList output;
    static int SCREEN_HEIGHT = 576;
    static int SCREEN_WIDTH = 1024;

    @BeforeAll
    static void setUp(){
        output = new ArrayList<>();
        output.add(0);
        output.add(0);
        output.add(0);
        output.add(0);
        inputToMovementOutput = new InputToMovementOutput();
        userInputButtons = new UserInputButtons(SCREEN_HEIGHT, SCREEN_WIDTH);
        joystickFinger = userInputButtons.processInput(266, 416);
        jumpFinger = userInputButtons.processInput(766, 425);
        shootFinger = userInputButtons.processInput(764, 525);
        placeFinger = userInputButtons.processInput(944, 520);
    }

    @BeforeEach
    void arrayListReset(){
        output.set(0, 0);
        output.set(1, 0);
        output.set(2, 0);
        output.set(3, 0);
    }

    @Test
    void shouldBeJump() {
        // Given
        output.set(1, 1);
        // When
        MovementFormat jump = inputToMovementOutput.getOutput(jumpFinger);
        // Then
        assertEquals(jump.getButtonsPressed(), output);
        assertEquals(jump.getJoystickInput(), new Vector2(0, 0));
    }

    @Test
    void shouldBeJoystick() {
        // Given
        output.set(0,1 );
        // When
        MovementFormat joystick = inputToMovementOutput.getOutput(joystickFinger);
        // Then
        assertEquals(joystick.getButtonsPressed(), output);
        assertEquals(joystick.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBeJoystickAndShoot() {
        // Given
        output.set(0, 1);
        output.set(2, 1);
        // When
        MovementFormat joystickAndShoot = inputToMovementOutput.getOutput(joystickFinger, shootFinger);
        // Then
        assertEquals(joystickAndShoot.getButtonsPressed(), output);
        assertEquals(joystickAndShoot.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBeJoystickShootPlace() {
        // Given
        output.set(0, 1);
        output.set(2, 1);
        output.set(3, 1);
        // When
        MovementFormat joystickShootPlace = inputToMovementOutput.getOutput(joystickFinger, shootFinger, placeFinger);
        // Then
        assertEquals(joystickShootPlace.getButtonsPressed(), output);
        assertEquals(joystickShootPlace.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBeJumpShootPlace() {
        // Given
        output.set(1, 1);
        output.set(2, 1);
        output.set(3, 1);
        // When
        MovementFormat jumpShootPlace = inputToMovementOutput.getOutput(jumpFinger, shootFinger, placeFinger);
        // Then
        assertEquals(jumpShootPlace.getButtonsPressed(), output);
        assertEquals(jumpShootPlace.getJoystickInput(), new Vector2(0, 0));
    }

}