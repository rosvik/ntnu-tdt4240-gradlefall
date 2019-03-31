package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.badlogic.ashley.core.Component;
import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementFormat;

/**
 * todo: Sende data til server.
 */
public class ControllerServerComponent implements Component {
    private float x;
    private float y;
    private boolean shoot;
    private float angle;
    private float magnitude;

    /**
     * Constructor.
     */
    public ControllerServerComponent() {
        this.x = 10;
        this.y = 10;
        shoot = false;
    }

    /**
     * Update the current controller.
     * todo: Should send data to server.
     * @param movementFormat = The current button presses from controller
     */
    public void  update(final MovementFormat movementFormat) {
        angle = movementFormat.getJoystickInput().x;
        magnitude = movementFormat.getJoystickInput().y;

        if (movementFormat.getButtonsPressed().get(1) == 1 && angle < 180) {
            y += 0.1f;
        } else if (movementFormat.getButtonsPressed().get(1) == 1 && angle > 180) {
            y -= 0.1f;
        }

        if (angle > 1 && angle < 90 || angle > 270) {
            this.x += 0.001f * magnitude;

        } else if (angle > 90 && angle < 270) {
            this.x -= 0.001f * magnitude;
        }

        if (movementFormat.getButtonsPressed().get(2) == 1) {
            shoot = true;
        } else {
            shoot = false;
        }
    }

    /**
     * @return the current x position
     */
    public float getX() {
        return x;
    }

    /**
     * @return the current y position
     */
    public float getY() {
        return y;
    }

    /**
     * @return true if shoot button is pressed
     */
    public boolean shootPressed() {
        return shoot;
    }

    /**
     * @return the angle of the controller
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @return the magnitude of the controller.
     */
    public float getMagnitude() {
        return magnitude;
    }

}
