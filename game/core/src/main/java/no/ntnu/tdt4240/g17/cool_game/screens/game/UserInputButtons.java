package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import lombok.Getter;

/**
 * Class that creates all input buttons based on the screen height and width.
 */
@Getter
public class UserInputButtons {

    private Rectangle joystickBox, jump, shoot, place, buttonBox;
    private Circle joystick;
    private int joystickRatio, screenHeight, screenWidth;
    ;
    private long joystickRadius;
    private Vector2 joystickCenter;

    /**
     * Constructor that calculates attributes and creates all the necessary buttons.
     *
     * @param screenHeight Height of the screen.
     * @param screenWidth  Width of the screen.
     */
    public UserInputButtons(final int screenHeight, final int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        joystickRatio = (screenWidth / 3 + screenHeight / 3) / 2;
        joystickBox = new Rectangle(Math.round(joystickRatio * 0.1),
                Math.round(joystickRatio * 0.1), joystickRatio, joystickRatio);
        joystickCenter = new Vector2(Math.round(joystickBox.width * (0.5)) + joystickBox.x,
                Math.round(joystickBox.height * (0.5)) + joystickBox.y);
        joystickRadius = Math.round(joystickBox.height * (0.4));
        joystick = new Circle(joystickCenter.x, joystickCenter.y, joystickRadius);
        buttonBox = new Rectangle(Math.round(screenWidth * (0.66)),
                0, Math.round(screenWidth * (0.34)), Math.round(screenHeight * (0.33)));
        jump = new Rectangle(buttonBox.x, Math.round(buttonBox.height * (0.5)),
                buttonBox.width, Math.round(buttonBox.height * (0.5)));
        shoot = new Rectangle(buttonBox.x, buttonBox.y,
                Math.round(buttonBox.width * (0.5)), Math.round(buttonBox.height * (0.5)));
        place = new Rectangle(buttonBox.x + shoot.width, buttonBox.y,
                Math.round(buttonBox.width * (0.5)), Math.round(buttonBox.height * (0.5)));
    }

    /**
     * @param x X-position of touch input
     * @param y Y-position of touch input
     * @return which button is pressed and a vector with angle and magnitude if the joystick is pressed.
     * The vector is (0,0) if only a button is pressed
     */
    public MovementOutput processInput(final double x, final double y) {
        double relativeY = this.screenHeight - y;
        if (buttonBox.contains((float) x, (float) relativeY)) {
            return new MovementOutput(calculateButtonInput((float) x, (float) relativeY), new Vector2(0, 0));
        } else if (joystickBox.contains((float) x, (float) relativeY)) {
            return new MovementOutput("joystick", calculateJoystickInput(x, y));
        }
        return new MovementOutput("No overlap", new Vector2(0, 0));
    }

    /**
     * @param x X-position of touch input
     * @param y Y-position of touch input
     * @return the button which is currently pressed
     */
    private String calculateButtonInput(final float x, final float y) {
        if (jump.contains(x, y)) {
            return "jump";
        } else if (shoot.contains(x, y)) {
            return "shoot";
        } else if (place.contains(x, y)) {
            return "place";
        }
        return "";
    }

    /**
     * @param x X-position of touch input
     * @param y Y-position of touch input
     * @return angle and magnitude of the vector between the centre of the joystick and the touch input
     */
    private Vector2 calculateJoystickInput(final double x, final double y) {
        double relativeX = (x - joystickCenter.x);
        double relativeY = ((this.screenHeight - joystickCenter.y) - y);
        float angle = 0;
        if (relativeX >= 0 && relativeY > 0) {
            angle = (float) (Math.toDegrees(Math.atan((relativeY / relativeX))));
        }
        if (relativeX < 0) {
            angle = (float) (180 + Math.toDegrees(Math.atan((relativeY / relativeX))));
        } else if (relativeX >= 0 && relativeY < 0) {
            angle = (float) (360 + Math.toDegrees(Math.atan((relativeY / relativeX))));
        }
        float magnitude = (float) Math.sqrt(Math.pow(relativeX, 2) + Math.pow(relativeY, 2));
        return new Vector2(angle, Math.min(scaleNumber(magnitude), 100));
    }

    private float scaleNumber(float number) {
        return (100 * (number)) / joystickRadius;
    }
}
