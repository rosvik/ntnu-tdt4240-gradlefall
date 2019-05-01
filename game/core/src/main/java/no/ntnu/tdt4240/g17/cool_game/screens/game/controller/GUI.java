package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.badlogic.gdx.Gdx;

import lombok.Getter;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;

/**
 * Component for the controller.
 * Made with singleton design pattern.
 * @author Håvard 'havfar' Farestveit
 */
public final class GUI {
    private static GUI guiInstance;

    @Getter
    private InputProcessor inputProcessor;
    private TouchInput firstFinger, secondFinger, thirdFinger;
    @Getter
    private MovementFormat movementFormat;
    private int screenHeigth, screenWidth;
    @Getter
    private ControlsMessage lastMessage;

    /**
     * Singleton constructor.
     */
    private GUI() {
        screenHeigth = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        inputProcessor = new InputProcessor(screenHeigth, screenWidth);
        lastMessage = new ControlsMessage();
    }

    /**
     * Get the singleton instance.
     * @return This instance.
     */
    public static GUI getInstance() {
        if (guiInstance == null) {
            synchronized (GUI.class) {
                if (guiInstance == null) {
                    guiInstance = new GUI();
                }
            }
        }
        return guiInstance;
    }

    /**
     * Updates information from each controller input / finger.
     * Sends the data to server as a ControlsMessage.
     * @return the created lastMessage with all active controls.
     */
    public ControlsMessage update() {
        firstFinger = new TouchInput(Gdx.input.isTouched(0), Gdx.input.getX(0), Gdx.input.getY(0));
        secondFinger = new TouchInput(Gdx.input.isTouched(1), Gdx.input.getX(1), Gdx.input.getY(1));
        thirdFinger = new TouchInput(Gdx.input.isTouched(2), Gdx.input.getX(2), Gdx.input.getY(2));
        movementFormat = inputProcessor.processInput(firstFinger, secondFinger, thirdFinger);
        lastMessage.jump = movementFormat.getButtonsPressed().get(1) == 1;
        lastMessage.shoot = movementFormat.getButtonsPressed().get(2) == 1;
        lastMessage.moveAngleRadians = (float) Math.toRadians(movementFormat.getJoystickInput().x);
        lastMessage.shootAngleRadians = (float) Math.toRadians(movementFormat.getJoystickInput().x);
        lastMessage.moveSpeed = (float) Math.toRadians(movementFormat.getJoystickInput().y);
        lastMessage.placeBlock = false;
        lastMessage.placeBlockAngleRadians =  movementFormat.getJoystickInput().x;
        return lastMessage;
    }
}
