package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
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

    private static final int TOP_SPAN = 10;

    private Navigator navigator;
    private SettingsController settingsController;
    private SettingsModel settingsModel;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    private Stage stage;


    /**
     * @param navigator          reference to main.
     * @param settingsController controller
     * @param settingsModel      model
     */
    public SettingsView(final Navigator navigator, final SettingsController settingsController,
                        final SettingsModel settingsModel) {
        this.navigator = navigator;
        this.settingsModel = settingsModel;
        this.settingsController = settingsController;
    }

    /**
     * determine what to be shown of not.
     */
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        setUpUiComponents(table, skin);

    }

    /**
     * Create ui components and add them to the table.
     * @param table components are added to this
     * @param skin the visual appearance of all components
     */
    private void setUpUiComponents(final Table table, final Skin skin) {
        //volumbutton
        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        musicVolumeSlider.setValue(settingsModel.getMusicVolume());
        musicVolumeSlider.addListener(event -> {
            settingsController.changeMusicVolume(musicVolumeSlider.getValue());
            return true;
        });

        // sound volume
        final Slider soundVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundVolumeSlider.setValue(settingsModel.getSoundVolume());
        soundVolumeSlider.addListener(event -> {
            settingsController.changeSoundVolume(soundVolumeSlider.getValue());
            return true;
        });


        // music on/off
        CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(settingsModel.isMusicEnabled());
        musicCheckbox.addListener(event -> {
            boolean enabled = musicCheckbox.isChecked();
            settingsController.toggleMusic(enabled);
            return true;
        });

        // sound on/off
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(settingsModel.isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(event -> {
            boolean enabled = soundEffectsCheckbox.isChecked();     //enables checkbox
            settingsController.toggleSoundEffects(enabled);
            return false;
        });


        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                settingsController.backToHome(navigator);
            }
        });

        titleLabel = new Label("Preferences", skin);
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeSoundLabel = new Label("Sound Volume", skin);
        musicOnOffLabel = new Label("Music", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);
        table.add(volumeMusicLabel).left();
        table.add(musicVolumeSlider);
        table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);
        table.add(volumeSoundLabel).left();
        table.add(soundVolumeSlider);
        table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);
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
        stage.act(Gdx.graphics.getDeltaTime());
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
