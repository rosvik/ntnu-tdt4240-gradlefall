package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class MainGame extends ApplicationAdapter {
	int JOYSTICKRATIO;
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	String color;
	UserInputButtons ib;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font  = new BitmapFont();
		UserInputProcessor inputProcessor = new UserInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		shapeRenderer = new ShapeRenderer();
		color = "red";
		ib = new UserInputButtons(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(2);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(ib.joystickBox.x, ib.joystickBox.y, ib.joystickBox.width, ib.joystickBox.height);
		shapeRenderer.arc(ib.joystick.x, ib.joystick.y, ib.joystick.radius, 0, 360);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.line(new Vector2(Gdx.graphics.getWidth()/2,0), new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()));
		shapeRenderer.rect(ib.buttonBox.x, ib.buttonBox.y, ib.buttonBox.width, ib.buttonBox.height);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(ib.jump.x, ib.jump.y, ib.jump.width, ib.jump.height);
		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.rect(ib.shoot.x, ib.shoot.y, ib.shoot.width, ib.shoot.height);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(ib.place.x, ib.place.y, ib.place.width, ib.place.height);
		shapeRenderer.end();
		batch.begin();
		font.draw(batch, "x: " + Integer.toString(Gdx.input.getX()) + ", y: " + Integer.toString(Gdx.input.getY()), 10, 20);
		font.draw(batch, "Angle, Magnitude: " + ib.calculateJoystickInput(77,17), 500, 500);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
