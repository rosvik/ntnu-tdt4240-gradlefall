package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementFormat;

/**
 * Class that translates the MovementFormat to data that is sent to physics engine.
 */
public class InputToMovementOutput {

    float angle;

    public MovementFormat getOutput(final MovementFormat firstFinger, final MovementFormat secondFinger) {
           switch (firstFinger.getButtonInput()) {
               case "No overlap":
                   return new MovementFormat("nothing", 0);
               case "place":
                   return new MovementFormat("place", 0);
               case "joystick":
                   angle = firstFinger.getJoystickInput().x;
                   if (secondFinger.getButtonInput() == "No overlap") {
                       return new MovementFormat("nothing", 0);
                   }
                   if (secondFinger.getButtonInput() == "place") {
                       return new MovementFormat("place", 0);
                   }
                   if (secondFinger.getButtonInput() == "shoot") {
                       return new MovementFormat("shoot", angle);
                   }
                   if (secondFinger.getButtonInput() == "jump") {
                       return new MovementFormat("jump", 0);
                   }
                   if (angle > 90 && angle < 270) {
                       return new MovementFormat("left", firstFinger.getJoystickInput().y);
                   } else {
                       return new MovementFormat("right", firstFinger.getJoystickInput().y);
                   }
               case "jump":
                   return new MovementFormat("jump", 0);
               case "shoot":
                   if (secondFinger.getButtonInput() == "joystick") {
                       return new MovementFormat("shoot", secondFinger.getJoystickInput().x);
                   } else {
                       return new MovementFormat("shoot", 90);
                   }
           }
        return null;
    }

    public MovementFormat getOutput(final MovementFormat firstFinger) {
        switch (firstFinger.getButtonInput()) {
            case "No overlap":
                return new MovementFormat("nothing", 0);
            case "place":
                return new MovementFormat("place", 0);
            case "joystick":
                angle = firstFinger.getJoystickInput().x;
                if (angle > 90 && angle < 270) {
                    return new MovementFormat("left", firstFinger.getJoystickInput().y);
                } else {
                    return new MovementFormat("right", firstFinger.getJoystickInput().y);
                }
            case "jump":
                return new MovementFormat("jump", 0);
            case "shoot":
                return new MovementFormat("shoot", 90);
        }
        return null;
    }
}
