package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import lombok.Getter;

/**
 * TouchInput object that contains whether a touch is active, and it x-y-coordinates.
 */
@Getter
public class TouchInput {

    private boolean isPressed;
    private float xPosition, yPosition;

    /**
     * Constructor for the TouchInput object.
     * @param isPressed whether the finger is pressed down or not
     * @param xPosition x-coordinate of touch
     * @param yPosition y-coordinate of touch
     */
    public TouchInput(final boolean isPressed, final float xPosition, final float yPosition) {
        this.isPressed = isPressed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
