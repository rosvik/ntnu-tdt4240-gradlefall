package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import lombok.Getter;

/**
 * Combines which button is pressed and angle and magnitude of joystickVector.
 */
@Getter
public class MovementOutput {

    /** State of the button presses. */
    private String buttonOutput;
    /** State of the joystick. */
    private Vector2 joystickOutput;

    /**
     * Create a new state object for output.
     * @param buttonOutput button state
     * @param joystickOutput joystick state
     */
    public MovementOutput(final String buttonOutput, final Vector2 joystickOutput) {
        this.buttonOutput = buttonOutput;
        this.joystickOutput = joystickOutput;
    }

}
