package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Combines which button is pressed and angle and magnitude of joystickVector.
 */
public class MovementOutput {

    private String buttonOutput;
    private Vector2 joystickOutput;

    public MovementOutput(final String buttonOutput, final Vector2 joystickOutput) {
        this.buttonOutput = buttonOutput;
        this.joystickOutput = joystickOutput;
    }

    public String getButtonOutput() {
        return buttonOutput;
    }

    public Vector2 getJoystickOutput() {
        return joystickOutput;
    }
}
