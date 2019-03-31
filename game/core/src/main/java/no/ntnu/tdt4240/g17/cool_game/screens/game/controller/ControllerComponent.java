package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import no.ntnu.tdt4240.g17.cool_game.screens.game.InputProcessor;
import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementFormat;
import no.ntnu.tdt4240.g17.cool_game.screens.game.TouchInput;

/**
 * Component for the controller.
 * Made with singleton design pattern.
 * @author Håvard 'havfar' Farestveit
 */
public final class ControllerComponent implements Component {
    private InputProcessor inputProcessor;
    private TouchInput firstFinger, secondFinger, thirdFinger;
    private MovementFormat movementFormat;
    private int screenHeigth, screenWidth;
    private static ControllerComponent controllerComponent;

    /**
     * Singleton constructor.
     */
    private ControllerComponent() {
        screenHeigth = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        inputProcessor = new InputProcessor(screenHeigth, screenWidth);
    }

    /**
     * Get the singleton instance.
     * @return This instance.
     */
    public static ControllerComponent getInstance() {
        if (controllerComponent == null) {
            controllerComponent = new ControllerComponent();
        }
        return controllerComponent;
    }

    /**
     * Updates information from each controller input / finger.
     */
    public void update() {
        firstFinger = new TouchInput(Gdx.input.isTouched(0), Gdx.input.getX(0), Gdx.input.getY(0));
        secondFinger = new TouchInput(Gdx.input.isTouched(1), Gdx.input.getX(1), Gdx.input.getY(1));
        thirdFinger = new TouchInput(Gdx.input.isTouched(2), Gdx.input.getX(2), Gdx.input.getY(2));
        movementFormat = inputProcessor.processInput(firstFinger, secondFinger, thirdFinger);
    }

    /**
     * @return the current control value.
     */
    public MovementFormat getMovementFormat() {
        return movementFormat;
    }
}
