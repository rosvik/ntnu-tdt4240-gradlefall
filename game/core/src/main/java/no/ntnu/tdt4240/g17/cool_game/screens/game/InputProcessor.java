package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.g17.cool_game.character.InputToMovementOutput;

/**
 * Class that takes in data of three fingers from the screen.
 * Calls on the correct inputToMovementOutput function based on how many fingers are pressed.
 * Then returns a movementFormat with all necessary info to the calling class.
 */

public class InputProcessor {

    MovementFormat firstFinger, secondFinger, thirdFinger, movementOutput;
    UserInputButtons userInputButtons;
    InputToMovementOutput inputToMovementOutput;


    /**
     * Constructor.
     * @param screenHeight height of the screen
     * @param screenWidth width of the screen
     */
    public InputProcessor(final int screenHeight, final int screenWidth) {
        userInputButtons = new UserInputButtons(screenHeight, screenWidth);
        inputToMovementOutput = new InputToMovementOutput();
    }

    /**
     * @param fingerOnePressed Whether the first finger is touching the screen or not
     * @param fingerTwoPressed Whether the second finger is touching the screen or not
     * @param fingerThreePressed Whether the third finger is touching the screen or not
     * @param fingerOneX x-ccordinate of the first finger
     * @param fingerOneY y-coordinate of the first finger
     * @param fingerTwoX x-coordinate of the second finger
     * @param fingerTwoY y-coordinate of the second finger
     * @param fingerThreeX x-coordinate of the third finger
     * @param fingerThreeY y-coordinate of the third finger
     * @return The server-accepted movementFormat that inputToMovementOutput returns
     */
    public MovementFormat processInput(final boolean fingerOnePressed, final boolean fingerTwoPressed,
                                       final boolean fingerThreePressed, final double fingerOneX,
                                       final double fingerOneY, final double fingerTwoX, final double fingerTwoY,
                                       final double fingerThreeX, final double fingerThreeY) {
        if (fingerOnePressed && fingerTwoPressed && fingerThreePressed) {
            firstFinger = userInputButtons.processInput(fingerOneX, fingerOneY);
            secondFinger = userInputButtons.processInput(fingerTwoX, fingerTwoY);
            thirdFinger = userInputButtons.processInput(fingerThreeX, fingerThreeY);
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger, thirdFinger);
        } else if (fingerOnePressed && fingerTwoPressed) {
            firstFinger = userInputButtons.processInput(fingerOneX, fingerOneY);
            secondFinger = userInputButtons.processInput(fingerTwoX, fingerTwoY);
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger);
        } else if (fingerOnePressed && fingerThreePressed) {
            firstFinger = userInputButtons.processInput(fingerOneX, fingerOneY);
            secondFinger = userInputButtons.processInput(fingerThreeX, fingerThreeY);
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger);
        } else if (fingerTwoPressed && fingerThreePressed) {
            firstFinger = userInputButtons.processInput(fingerTwoX, fingerTwoY);
            secondFinger = userInputButtons.processInput(fingerThreeX, fingerThreeY);
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger);
        } else if (fingerOnePressed) {
            firstFinger = userInputButtons.processInput(fingerOneX, fingerOneY);
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        } else if (fingerTwoPressed) {
            firstFinger = userInputButtons.processInput(fingerTwoX, fingerTwoY);
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        } else if (fingerThreePressed) {
            firstFinger = userInputButtons.processInput(fingerThreeX, fingerThreeY);
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        } else {
            firstFinger = new MovementFormat("none pressed", new Vector2(0, 0));
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        }
        return movementOutput;
    }
}
