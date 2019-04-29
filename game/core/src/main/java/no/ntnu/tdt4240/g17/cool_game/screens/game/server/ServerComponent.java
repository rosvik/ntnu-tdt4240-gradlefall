package no.ntnu.tdt4240.g17.cool_game.screens.game.server;

import com.badlogic.ashley.core.Component;
import no.ntnu.tdt4240.g17.cool_game.character.TestMovementAPI;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.ControllerServerComponent;

/**
 * Component for the server.
 * @author Håvard 'havfar' Farestveit
 */
public class ServerComponent implements Component {

    private TestMovementAPI api;
    private ControllerServerComponent controllerServerComponent;
    float x;
    float y;
    boolean shoot;
    float angle;
    boolean prevShootValue = false;
    private int playerId;

    /**
     * Constructor.
     * @param testMovementAPI = Local server
     * @param controllerServerComponent = local controller server
     */
    public ServerComponent(final int ID,
            final TestMovementAPI testMovementAPI,
            final ControllerServerComponent controllerServerComponent) {
        x = 0;
        y = 0;
        playerId = ID;
        api = testMovementAPI;
        this.controllerServerComponent = controllerServerComponent;
    }

    /**
     * Fetches data from temporary servers.
     * todo: Hente data fra server.
     */
    public void fetchFromAPI() {
        if (api != null) {
            api.changeXvalue();
            x = api.getxValue();
            y = api.getyValue();
            shoot = false;
            angle = 0;
        } else {
            x = controllerServerComponent.getX();
            y = controllerServerComponent.getY();
            shoot = controllerServerComponent.shootPressed();
            angle = controllerServerComponent.getAngle();
        }
    }

    /**
     * @return the current x position from server
     */
    public float getX() {
        return  x;
    }

    /**
     * @return the current y position from server
     */
    public float getY() {
        return y;
    }

    /**
     * @return true if shootButton is pressed
     */
    public boolean shootPressed() {
        if (shoot && !prevShootValue) {
            prevShootValue = shoot;
            return true;
        } else {
            prevShootValue = shoot;
            return false;
        }
    }

    /**
     * @return the current angle
     */
    public float getAngle() {
        return angle;
    }
}
