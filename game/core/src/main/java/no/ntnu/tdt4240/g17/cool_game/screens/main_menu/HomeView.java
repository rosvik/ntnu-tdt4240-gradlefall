package no.ntnu.tdt4240.g17.cool_game.screens.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 *
 */
public class HomeView implements Screen {

    /**
     * how big part of the screen.
     */
    public static final float FRACTIONSCREEN = 30f;
    /**
     * ROW.
     */
    public static final int ROW = 10;

    private HomeController homeController;

    /**
     * stageclass.
     */
    private Stage stage;

    /**
     * @param homeController needs to take in homecontroler.
     */
    public HomeView(final HomeController homeController) {
        this.homeController = homeController;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / FRACTIONSCREEN));
        stage.draw();
    }

    /**
     * bs javac.
     */
    @Override
    public void show() {
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        //create buttons with skin
        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        TextButton play = new TextButton("PLAY", skin);
        TextButton settings = new TextButton("SETTINGS", skin);
        TextButton quit = new TextButton("QUIT", skin);

        //adds buttons to table
        table.add(play).fillX().uniformX();
        table.row().pad(ROW, 0, ROW, 0);
        table.add(settings).fillX().uniformX();
        table.row();
        table.add(quit).fillX().uniformX();

        //creates button listeners,
        // action for settings
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                homeController.changeToSettings();
            }
        });



        // action for exit
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                homeController.quit();
            }
        });
    }

    /**
     * @param delta
     */
    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / FRACTIONSCREEN));
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
    }
}
