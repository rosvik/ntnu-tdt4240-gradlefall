package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.MovementFormat;

/**
 * Class that translates touch input to data that is sent to physics engine.
 */
public class InputToMovementOutput {

    Vector2 joystickInput;
    ArrayList<Integer> buttonsPressed;


    /**
     * Creating the necessary things.
     */
    public InputToMovementOutput() {
        joystickInput = new Vector2(0, 0);
        buttonsPressed = new ArrayList<Integer>();
        buttonsPressed.add(0);
        buttonsPressed.add(0);
        buttonsPressed.add(0);
        buttonsPressed.add(0);

    }

    /**
     * @param firstFinger MovementFormat telling what button the finger is pressing and x-y-coordinates.
     * @param secondFinger MovementFormat telling what button the finger is pressing and x-y-coordinates.
     * @param thirdFinger MovementFormat telling what button the finger is pressing and x-y-coordinates.
     * @return MovementFormat, consisting of Array[0,0,0,0] and Vector2(angle, magnitude) that is sent to server.
     *         The Array shows whether [joystick, jump, shoot, place] buttons is pressed or not.
     */
    public MovementFormat getOutput(final MovementFormat firstFinger, final MovementFormat secondFinger,
                                    final MovementFormat thirdFinger) {
        joystickInput = new Vector2(0, 0);
        buttonsPressed.set(0, 0);
        buttonsPressed.set(1, 0);
        buttonsPressed.set(2, 0);
        buttonsPressed.set(3, 0);
        switch (firstFinger.getButtonInput()) {
            case "joystick":
                buttonsPressed.set(0, 1);
                joystickInput = new Vector2(firstFinger.getJoystickInput().x, firstFinger.getJoystickInput().y);
                break;
            case "jump":
                buttonsPressed.set(1, 1);
                break;
            case "shoot":
                buttonsPressed.set(2, 1);
                break;
            case "place":
                buttonsPressed.set(3, 1);
                break;
            default:
                break;
        }
        switch (secondFinger.getButtonInput()) {
            case "joystick":
                buttonsPressed.set(0, 1);
                joystickInput = new Vector2(secondFinger.getJoystickInput().x, secondFinger.getJoystickInput().y);
                break;
            case "jump":
                buttonsPressed.set(1, 1);
                break;
            case "shoot":
                buttonsPressed.set(2, 1);
                break;
            case "place":
                buttonsPressed.set(3, 1);
                break;
            default:
                break;
        }
        switch (thirdFinger.getButtonInput()) {
            case "joystick":
                buttonsPressed.set(0, 1);
                joystickInput = new Vector2(thirdFinger.getJoystickInput().x, thirdFinger.getJoystickInput().y);
                break;
            case "jump":
                buttonsPressed.set(1, 1);
                break;
            case "shoot":
                buttonsPressed.set(2, 1);
                break;
            case "place":
                buttonsPressed.set(3, 1);
                break;
            default:
                break;
        }
        return new MovementFormat(buttonsPressed, joystickInput);
    }

    /**
     * @param firstFinger MovementFormat telling what button the finger is pressing and x-y-coordinates.
     * @param secondFinger MovementFormat telling what button the finger is pressing and x-y-coordinates.
     * @return MovementFormat, consisting of Array[0,0,0,0] and Vector2(angle, magnitude) that is sent to server.
     *         The Array shows whether [joystick, jump, shoot, place] buttons is pressed or not.
     */
    public MovementFormat getOutput(final MovementFormat firstFinger, final MovementFormat secondFinger) {
        joystickInput = new Vector2(0, 0);
        buttonsPressed.set(0, 0);
        buttonsPressed.set(1, 0);
        buttonsPressed.set(2, 0);
        buttonsPressed.set(3, 0);
        switch (firstFinger.getButtonInput()) {
            case "joystick":
                buttonsPressed.set(0, 1);
                joystickInput = new Vector2(firstFinger.getJoystickInput().x, firstFinger.getJoystickInput().y);
                break;
            case "jump":
                buttonsPressed.set(1, 1);
                break;
            case "shoot":
                buttonsPressed.set(2, 1);
                break;
            case "place":
                buttonsPressed.set(3, 1);
                break;
            default:
                break;
        }
        switch (secondFinger.getButtonInput()) {
            case "joystick":
                buttonsPressed.set(0, 1);
                joystickInput = new Vector2(secondFinger.getJoystickInput().x, secondFinger.getJoystickInput().y);
                break;
            case "jump":
                buttonsPressed.set(1, 1);
                break;
            case "shoot":
                buttonsPressed.set(2, 1);
                break;
            case "place":
                buttonsPressed.set(3, 1);
                break;
            default:
                break;
        }
        return new MovementFormat(buttonsPressed, joystickInput);
    }

    /**
     * @param firstFinger MovementFormat telling what button the finger is pressing and x-y-coordinates.
     * @return MovementFormat, consisting of Array[0,0,0,0] and Vector2(angle, magnitude) that is sent to server.
     *         The Array shows whether [joystick, jump, shoot, place] buttons is pressed or not.
     */
    public  MovementFormat getOutput(final MovementFormat firstFinger) {
        joystickInput = new Vector2(0, 0);
        buttonsPressed.set(0, 0);
        buttonsPressed.set(1, 0);
        buttonsPressed.set(2, 0);
        buttonsPressed.set(3, 0);
        switch (firstFinger.getButtonInput()) {
            case "joystick":
                buttonsPressed.set(0, 1);
                joystickInput = new Vector2(firstFinger.getJoystickInput().x, firstFinger.getJoystickInput().y);
                break;
            case "jump":
                buttonsPressed.set(1, 1);
                break;
            case "shoot":
                buttonsPressed.set(2, 1);
                break;
            case "place":
                buttonsPressed.set(3, 1);
                break;
            default:
                break;
        }
        return new MovementFormat(buttonsPressed, joystickInput);
    }
}
