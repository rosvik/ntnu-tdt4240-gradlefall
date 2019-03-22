package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class MovementFormatTest {

    MovementFormat movementFormat;

    @Test
    void buttonOutputShouldBeString() {
        // Given
        movementFormat = new MovementFormat("jump", new Vector2(0,0));
        // When

        // Then
        assertThat(movementFormat.getButtonInput(), isA(String.class));
    }

    @Test
    void joystickOutputShouldBeVector2() {
        // Given
        movementFormat = new MovementFormat("jump", new Vector2(0,0));
        // When

        // Then
        assertThat(movementFormat.getJoystickInput(), isA(Vector2.class));
    }
}