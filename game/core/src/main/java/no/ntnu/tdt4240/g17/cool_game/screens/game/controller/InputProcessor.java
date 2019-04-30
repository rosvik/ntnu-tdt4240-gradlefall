package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

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
     *
     * @param fingerOne TouchInput object representing the first finger touching the screen.
     * @param fingerTwo TouchInput object representing the second finger touching the screen.
     * @param fingerThree TouchInput object representing the third finger touching the screen.
     * @return The server-accepted movementFormat that inputToMovementOutput returns.
     */
    public MovementFormat processInput(final TouchInput fingerOne, final TouchInput fingerTwo,
                                       final TouchInput fingerThree) {
        if (fingerOne.isPressed() && fingerTwo.isPressed() && fingerThree.isPressed()) {
            firstFinger = userInputButtons.processInput(fingerOne.getXPosition(), fingerOne.getYPosition());
            secondFinger = userInputButtons.processInput(fingerTwo.getXPosition(), fingerTwo.getYPosition());
            thirdFinger = userInputButtons.processInput(fingerThree.getXPosition(), fingerThree.getYPosition());
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger, thirdFinger);
        } else if (fingerOne.isPressed() && fingerTwo.isPressed()) {
            firstFinger = userInputButtons.processInput(fingerOne.getXPosition(), fingerOne.getYPosition());
            secondFinger = userInputButtons.processInput(fingerTwo.getXPosition(), fingerTwo.getYPosition());
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger);
        } else if (fingerOne.isPressed() && fingerThree.isPressed()) {
            firstFinger = userInputButtons.processInput(fingerOne.getXPosition(), fingerOne.getYPosition());
            secondFinger = userInputButtons.processInput(fingerThree.getXPosition(), fingerThree.getYPosition());
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger);
        } else if (fingerTwo.isPressed() && fingerThree.isPressed()) {
            firstFinger = userInputButtons.processInput(fingerTwo.getXPosition(), fingerTwo.getYPosition());
            secondFinger = userInputButtons.processInput(fingerThree.getXPosition(), fingerThree.getYPosition());
            movementOutput = inputToMovementOutput.getOutput(firstFinger, secondFinger);
        } else if (fingerOne.isPressed()) {
            firstFinger = userInputButtons.processInput(fingerOne.getXPosition(), fingerOne.getYPosition());
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        } else if (fingerTwo.isPressed()) {
            firstFinger = userInputButtons.processInput(fingerTwo.getXPosition(), fingerTwo.getYPosition());
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        } else if (fingerThree.isPressed()) {
            firstFinger = userInputButtons.processInput(fingerThree.getXPosition(), fingerThree.getYPosition());
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        } else {
            firstFinger = new MovementFormat("none pressed", new Vector2(0, 0));
            movementOutput = inputToMovementOutput.getOutput(firstFinger);
        }
        return movementOutput;
    }
}
