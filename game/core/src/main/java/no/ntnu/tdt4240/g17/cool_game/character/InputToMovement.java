package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementOutput;

/**
 * Class that translates the MovementOutput to actual data that the Character can use to move
 */
public class InputToMovement {

    float angle, magnitude, ground;
    float gravity = -1;
    Vector2 velocity;

    /**
     * @param currentPosition Current position of character
     * @param movementOutput The output from the on-screen joystick and buttons
     * @param screenHeight Height of screen
     * @param stateTime Time from Gdx
     * @return The new position of the character
     */

    public Vector2 getNewMovement(final Vector2 currentPosition, final MovementOutput movementOutput,
                                  final int screenHeight, final float stateTime) {
        angle = movementOutput.getJoystickOutput().x;
        magnitude = movementOutput.getJoystickOutput().y;
        ground = screenHeight / 2;
        velocity = new Vector2(0, 0);
        if (movementOutput.getButtonOutput() == "jump") {
            // TODO: Return something useful
            velocity.y = 2;
            //return new Vector2(200, ground);
        }
        float newX = currentPosition.x;
        float newY = currentPosition.y;
        if (angle > 90 && angle < 270) {
            //newX = newX - 1;
            newX -= (magnitude * 0.03);
        } else if (angle >= 270 || angle <= 90) {
            newX += (magnitude * 0.03);
            //newX = newX + 1;
        }
        if (newY > ground) {
            velocity.add(0, gravity);
        }
        velocity.scl(stateTime);
        newY += velocity.y;
        if (newY < ground) {
            newY = ground;
        }
        velocity.scl(1 / stateTime);

        return new Vector2(newX, newY);
    }
}
