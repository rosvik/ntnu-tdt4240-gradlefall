package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import lombok.Getter;

/**
 * Combines which button is pressed and angle and magnitude of joystickVector.
 */
@Getter
public class MovementFormat {

    /** State of the button presses. */
    private String buttonInput;
    /** State of the joystick. */
    private Vector2 joystickInput;
    /** Either angle or magnitude, based on the button */
    private float value;

    /**
     * Create a new state object for output.
     * @param buttonInput button state
     * @param joystickInput joystick state
     */
    public MovementFormat(final String buttonInput, final Vector2 joystickInput) {
        this.buttonInput = buttonInput;
        this.joystickInput = joystickInput;
    }

    /**
     * The format that is sent to the physics engine.
     * @param buttonInput button that is pressed
     * @param value either angle or magnitude, based on the button
     */
    public MovementFormat(final String buttonInput, final float value) {
        this.buttonInput = buttonInput;
        this.value = value;
    }
}
