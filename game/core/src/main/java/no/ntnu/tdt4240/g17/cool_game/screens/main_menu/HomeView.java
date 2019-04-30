package no.ntnu.tdt4240.g17.cool_game.screens.main_menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class HomeView extends ApplicationAdapter implements Screen {

    private static final float STAGE_MIN_FPS = 1f / 30f;

    private HomeController homeController;

    /** Stage for GUI rendering. */
    private Stage stage;

    private SpriteBatch batch;
    private Texture texture;

    /**
     * music.
     */
    private Music music;

    /**
     * @param homeController needs to take in homecontroler.
     */
    public HomeView(final HomeController homeController) {
        this.homeController = homeController;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), STAGE_MIN_FPS));
        stage.draw();
    }

    /**
     * eq to create method.
     */
    @Override
    public void show() {
        //spritebatches
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("background.png"));
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);         //shows border line.
        stage.addActor(table);

        //create buttons with skin
        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        TextButton play = new TextButton("PLAY", skin);
        TextButton settings = new TextButton("SETTINGS", skin);
        TextButton quit = new TextButton("QUIT", skin);

        //set textsize in button
        float textSize = 4f;
        play.getLabel().setFontScale(textSize);
        settings.getLabel().setFontScale(textSize);
        quit.getLabel().setFontScale(textSize);

        //adds buttons to table, + fixing size on cells and spacing the table
        float spacing = 20f * Gdx.graphics.getDensity();
        float buttonHeight = 64f * Gdx.graphics.getDensity();
        float buttonWidth = 192f * Gdx.graphics.getDensity();

        table.add(play)
                .prefHeight(buttonHeight)
                .prefWidth(buttonWidth)
                .padBottom(spacing);
        table.row();
        table.add(settings)
                .prefHeight(buttonHeight)
                .prefWidth(buttonWidth)
                .padBottom(spacing);
        table.row();
        table.add(quit)
                .prefHeight(buttonHeight)
                .prefWidth(buttonWidth);


        //creates button listeners,
        // action for settings
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                homeController.changeToSettings();
            }
        });

        play.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                homeController.changeToGame();
            }
        });

        // action for exit
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                homeController.quit();
            }
        });

        table.layout();
        log.info("Table width {}", table.getWidth());


        music = Gdx.audio.newMusic(Gdx.files.internal("Tune1.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
        //homeController.startMusic();
    }

    /**
     * @param delta
     */
    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);   //clears the screen

        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        //the buttons
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), STAGE_MIN_FPS));
        stage.draw();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * dispose of assets when not needed.
     */
    @Override
    public void dispose() {

        stage.dispose();
        music.dispose();
    }
}
