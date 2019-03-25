package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputProcessorTest {

    static InputProcessor inputProcessor;
    static MovementFormat movementOutput;
    static int SCREEN_HEIGHT = 576;
    static int SCREEN_WIDTH = 1024;
    static ArrayList output;
    static TouchInput joystickFinger, jumpFinger, shootFinger, placeFinger, emptyFinger;

    @BeforeAll
    static void setUp(){
        inputProcessor = new InputProcessor(SCREEN_HEIGHT, SCREEN_WIDTH);
        jumpFinger = new TouchInput(true,766,425);
        joystickFinger = new TouchInput(true,266,416);
        shootFinger = new TouchInput(true, 764, 525);
        placeFinger = new TouchInput(true, 944, 520);
        emptyFinger = new TouchInput(false, 0,0);
        output = new ArrayList<>();
        output.add(0);
        output.add(0);
        output.add(0);
        output.add(0);

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
        movementOutput = inputProcessor.processInput(jumpFinger, emptyFinger, emptyFinger);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 0));
    }

    @Test
    void shouldBeJoystick() {
        // Given
        output.set(0,1 );
        // When
        movementOutput = inputProcessor.processInput(emptyFinger, emptyFinger, joystickFinger);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBeJoystickAndShoot() {
        // Given
        output.set(0, 1);
        output.set(2, 1);
        // When
        movementOutput = inputProcessor.processInput(joystickFinger, shootFinger, emptyFinger);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBeJoystickAndJump() {
        // Given
        output.set(0, 1);
        output.set(1, 1);
        // When
        movementOutput = inputProcessor.processInput(joystickFinger, emptyFinger, jumpFinger);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBeJoystickShootPlace() {
        // Given
        output.set(0, 1);
        output.set(2, 1);
        output.set(3, 1);
        // When
        movementOutput = inputProcessor.processInput(joystickFinger, shootFinger, placeFinger);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 100));
    }

    @Test
    void shouldBeJumpShootPlace() {
        // Given
        output.set(1, 1);
        output.set(2, 1);
        output.set(3, 1);
        // When
        movementOutput = inputProcessor.processInput(jumpFinger, shootFinger, placeFinger);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 0));
    }

}
