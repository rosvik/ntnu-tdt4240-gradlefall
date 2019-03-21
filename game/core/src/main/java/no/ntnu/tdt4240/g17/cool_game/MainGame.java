package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
import no.ntnu.tdt4240.g17.cool_game.character.InputToMovement;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.screens.game.MovementOutput;
import no.ntnu.tdt4240.g17.cool_game.screens.game.UserInputButtons;

/**
 * Main game class.
 */
public class MainGame extends ApplicationAdapter {
    /** Batch to render. */
    SpriteBatch batch;

    /** Arena. */
    Arena arena;

    TextureAtlas atlas;

    GameCharacter character;

    float stateTime;

    UserInputButtons userInputButtons;

    MovementOutput movementOutput;

    InputToMovement inputToMovement;

    Vector2 oldPosition, newPosition;

    int screenHeigth, screenWidth;

    ShapeRenderer shapeRenderer;

    BitmapFont font;

    @Override
    public final void create() {
        font = new BitmapFont();
        screenHeigth = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        batch = new SpriteBatch();
        //arena = new Arena("map2.tmx", 16f, 32f, 20f, batch);
        //arena.setBackground("background.png");
        atlas = new TextureAtlas("DungeonTileset.atlas");
        character = new GameCharacter("big_zombie", 100,  100, atlas);
        stateTime = 0;
        userInputButtons = new UserInputButtons(screenHeigth, screenWidth);
        inputToMovement = new InputToMovement();
        oldPosition = new Vector2(screenWidth / 2, screenHeigth / 2);
        newPosition = new Vector2(0, 0);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public final void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.arc(userInputButtons.getJoystick().x, userInputButtons.getJoystick().y, userInputButtons.getJoystick().radius, 0, 360);
        shapeRenderer.line(0, screenHeigth / 2, screenWidth, screenHeigth / 2);
        shapeRenderer.rect(userInputButtons.getJump().x, userInputButtons.getJump().y, userInputButtons.getJump().width, userInputButtons.getJump().height);
        shapeRenderer.end();
        batch.begin();

        // Render the arena
        //arena.render();
        if (Gdx.input.isTouched()) {
            movementOutput = userInputButtons.processInput(Gdx.input.getX(), Gdx.input.getY());
        } else {
            movementOutput = new MovementOutput("joystick", new Vector2(0, 0));
        }
        newPosition = inputToMovement.getNewMovement(oldPosition, movementOutput, screenHeigth, stateTime);
        character.render(newPosition.x, newPosition.y);
        oldPosition = newPosition;

        //character.render((int) ((userInputButtons.processInput(Gdx.input.getX() , Gdx.input.getY())).getJoystickOutput().y) + screenWidth/2, screenHeigth/2);
        character.render(oldPosition.x, oldPosition.y);
        character.draw(batch, stateTime);
        //batch.draw();

        //batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public final void dispose() {
        batch.dispose();
    }
}
