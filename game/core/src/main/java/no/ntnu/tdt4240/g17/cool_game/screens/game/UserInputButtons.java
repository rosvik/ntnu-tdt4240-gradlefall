package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class UserInputButtons {

    Rectangle joystickBox, jump, shoot, place, buttonBox;
    Circle joystick;
    private int JOYSTICKRATIO;

    UserInputButtons(int screenHeigth, int screenWidth){
        JOYSTICKRATIO = (Gdx.graphics.getWidth()/3 + Gdx.graphics.getHeight()/3)/2;
        joystickBox = new Rectangle(Math.round(JOYSTICKRATIO*0.1), Math.round(JOYSTICKRATIO*0.1), JOYSTICKRATIO, JOYSTICKRATIO);
        joystick = new Circle(Math.round(joystickBox.width*(0.5))+joystickBox.x, Math.round(joystickBox.height*(0.5))+joystickBox.y, Math.round(joystickBox.height*(0.4)));
        buttonBox = new Rectangle(Math.round(screenWidth*(0.66)), 0, Math.round(screenWidth*(0.34)), Math.round(screenHeigth*(0.33)));
        jump = new Rectangle(buttonBox.x, Math.round(buttonBox.height*(0.5)), buttonBox.width, Math.round(buttonBox.height*(0.5)));
        shoot = new Rectangle(buttonBox.x, buttonBox.y, Math.round(buttonBox.width*(0.5)), Math.round(buttonBox.height*(0.5)));
        place = new Rectangle(buttonBox.x + shoot.width, buttonBox.y, Math.round(buttonBox.width*(0.5)), Math.round(buttonBox.height*(0.5)));
    }
    public Vector2 calculateJoystickInput(double x, double y){
        float angle = (float)Math.toDegrees(Math.atan((y/x)));
        float magnitude = (float)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        return new Vector2(angle, magnitude);
    }
}
