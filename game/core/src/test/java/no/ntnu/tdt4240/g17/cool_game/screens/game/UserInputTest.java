package no.ntnu.tdt4240.g17.cool_game.screens.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * By running this class you are able to visibly see the UI controls and test if input gives the expected output
 */

public final class UserInputTest extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont font;
    ShapeRenderer shapeRenderer;
    UserInputButtons ib;
    MovementOutput output;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font  = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        ib = new UserInputButtons(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
        output = new MovementOutput("", new Vector2(0, 0));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(2);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(ib.getJoystickBox().x, ib.getJoystickBox().y, ib.getJoystickBox().width, ib.getJoystickBox().height);
        shapeRenderer.arc(ib.getJoystick().x, ib.getJoystick().y, ib.getJoystick().radius, 0, 360);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(ib.getButtonBox().x, ib.getButtonBox().y, ib.getButtonBox().width, ib.getButtonBox().height);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(ib.getJump().x, ib.getJump().y, ib.getJump().width, ib.getJump().height);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(ib.getShoot().x, ib.getShoot().y, ib.getShoot().width, ib.getShoot().height);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(ib.getPlace().x, ib.getPlace().y, ib.getPlace().width, ib.getPlace().height);
        shapeRenderer.end();
        batch.begin();
        font.draw(batch, "x: " + Integer.toString(Gdx.input.getX()) + ", y: " + Integer.toString(Gdx.input.getY()), 10, 20);
        font.draw(batch, "Angle, Magnitude: " + output.getJoystickOutput() + ", Button: " + output.getButtonOutput(), 500, 400);
        if (Gdx.input.isTouched()) {
            output = ib.processInput(Gdx.input.getX(), Gdx.input.getY());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

