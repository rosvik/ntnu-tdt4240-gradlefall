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

    @BeforeAll
    static void setUp(){
        inputProcessor = new InputProcessor(SCREEN_HEIGHT, SCREEN_WIDTH);
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
        movementOutput = inputProcessor.processInput(true,false,false,766,425,0,0,0,0);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 0));
    }

    @Test
    void shouldBeJoystick() {
        // Given
        output.set(0,1 );
        // When
        movementOutput = inputProcessor.processInput(false,false,true,0,0,0,0,266,416);
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
        movementOutput = inputProcessor.processInput(true,true,false,266,416,764, 525,0,0);
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
        movementOutput = inputProcessor.processInput(true,false,true,266,416,0, 0,766,425);
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
        movementOutput = inputProcessor.processInput(true,true,true,266,416,764, 525,944, 520);
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
        movementOutput = inputProcessor.processInput(true,true,true,766, 425,764, 525,944, 520);
        // Then
        assertEquals(movementOutput.getButtonsPressed(), output);
        assertEquals(movementOutput.getJoystickInput(), new Vector2(0, 0));
    }

}
