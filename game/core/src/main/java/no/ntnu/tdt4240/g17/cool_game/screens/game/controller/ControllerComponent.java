package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;

import lombok.Getter;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.network.NetworkSettings;
import no.ntnu.tdt4240.g17.cool_game.screens.game.InputProcessor;
import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementFormat;
import no.ntnu.tdt4240.g17.cool_game.screens.game.TouchInput;

/**
 * Component for the controller.
 * Made with singleton design pattern.
 * @author Håvard 'havfar' Farestveit
 */
@Getter
public final class ControllerComponent implements Component {
    private InputProcessor inputProcessor;
    private TouchInput firstFinger, secondFinger, thirdFinger;
    private MovementFormat movementFormat;
    private int screenHeigth, screenWidth;
    private static ControllerComponent controllerComponent;
    private GameClient gameClient;
    private ControlsMessage message;

    /**
     * Singleton constructor.
     */
    private ControllerComponent() {
        screenHeigth = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        gameClient = new GameClient(NetworkSettings.getServerIp(), NetworkSettings.getPort(), new ClientData());
        inputProcessor = new InputProcessor(screenHeigth, screenWidth);
        message = new ControlsMessage();
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
     * Sends the data to server as a ControlsMessage.
     */
    public void update() {
        firstFinger = new TouchInput(Gdx.input.isTouched(0), Gdx.input.getX(0), Gdx.input.getY(0));
        secondFinger = new TouchInput(Gdx.input.isTouched(1), Gdx.input.getX(1), Gdx.input.getY(1));
        thirdFinger = new TouchInput(Gdx.input.isTouched(2), Gdx.input.getX(2), Gdx.input.getY(2));
        movementFormat = inputProcessor.processInput(firstFinger, secondFinger, thirdFinger);
        if (movementFormat.getButtonsPressed().get(1) == 1) {
            message.jump = true;
        }
        if (movementFormat.getButtonsPressed().get(2) == 1) {
            message.shoot = true;
        }
        message.moveAngle = movementFormat.getJoystickInput().x;
        message.shootAngle = movementFormat.getJoystickInput().x;
        message.moveSpeed = movementFormat.getJoystickInput().y;
        message.placeBlock = false;
        message.placeBlockAngle =  movementFormat.getJoystickInput().x;
        //gameClient.send(message);
    }
}
