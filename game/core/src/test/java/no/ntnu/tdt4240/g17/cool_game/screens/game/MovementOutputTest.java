package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class MovementOutputTest {

    MovementOutput movementOutput;

    @Test
    void buttonOutputShouldBeString() {
        // Given
        movementOutput = new MovementOutput("jump", new Vector2(0,0));
        // When

        // Then
        assertThat(movementOutput.getButtonOutput(), isA(String.class));
    }

    @Test
    void joystickOutputShouldBeVector2() {
        // Given
        movementOutput = new MovementOutput("jump", new Vector2(0,0));
        // When

        // Then
        assertThat(movementOutput.getJoystickOutput(), isA(Vector2.class));
    }
}