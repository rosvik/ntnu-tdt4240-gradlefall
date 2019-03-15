package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

// https://www.gamedevelopment.blog/full-libgdx-game-tutorial-menu-control/

/**
 *
 */
public class SettingsView implements Screen {

    /**
     * gg.
     */
    public static final int TOPSPAN = 10;
    /**
     * g.
     */
    public static final float FRACTION = 30f;
    /**
     * @param mainClass Orchestrator class
     */
    private Navigator parent;
    /**
     * field for settingcontroller.
     */
    private SettingsController settingsController;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    /**
     * stageclass.
     */
    private Stage stage;

    /**
     * @param navigator          reference to main.
     * @param settingsController settingscontroller.
     */
    public SettingsView(final Navigator navigator, final SettingsController settingsController) {
        parent = navigator;
        this.settingsController = new SettingsController();
        stage = new Stage(new ScreenViewport());
    }

    /**
     * determine what to be shown of not.
     */
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("android/assets/skin/neon-ui.json"));

        //volumbutton
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(final Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        // sound volume
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(parent.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(final Event event) {
                parent.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });


        // music on/off
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(final Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        // sound on/off
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(final Event event) {
                /*boolean enabled = soundEffectsCheckbox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled(enabled);
                return false;*/
                settingsController.getMainPrefs(parent);
                return false;
            }
        });


        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                //parent.changeView(Navigator.HOME);
                settingsController.backToHome(parent);
            }
        });

        titleLabel = new Label("Preferences", skin);
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeSoundLabel = new Label("Sound Volume", skin);
        musicOnOffLabel = new Label("Music", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(TOPSPAN, 0, 0, TOPSPAN);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(TOPSPAN, 0, 0, TOPSPAN);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(TOPSPAN, 0, 0, TOPSPAN);
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(TOPSPAN, 0, 0, TOPSPAN);
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(TOPSPAN, 0, 0, TOPSPAN);
        table.add(backButton).colspan(2);

    }

    /**
     * @param delta
     */
    @Override
    public void render(final float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / FRACTION));
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

    @Override
    public void dispose() {

    }
}
