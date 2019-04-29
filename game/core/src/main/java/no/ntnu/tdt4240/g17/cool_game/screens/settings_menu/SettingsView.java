package no.ntnu.tdt4240.g17.cool_game.screens.settings_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

// https://www.gamedevelopment.blog/full-libgdx-game-tutorial-menu-control/

/**
 *
 */
public class SettingsView implements Screen {

    /**
     * tables vertical span.
     */
    private static final int TOP_SPAN = 20;

    private Navigator navigator;
    private SettingsController settingsController;
    private SettingsModel settingsModel;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    private Stage stage;
    private SpriteBatch batch;
    private Texture texture;


    /**
     * @param navigator          reference to main.
     * @param settingsController controller
     * @param settingsModel      model
     */
    public SettingsView(final Navigator navigator, final SettingsController settingsController,
                        final SettingsModel settingsModel) {
        stage = new Stage(new ScreenViewport());    //moved from showmethod
        this.navigator = navigator;
        this.settingsModel = settingsModel;
        this.settingsController = settingsController;
    }

    /**
     * determine what to be shown of not.
     */
    @Override
    public void show() {
        //spritebatches
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("background.png"));

        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);


        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        setUpUiComponents(table, skin);

    }

    /**
     * Create ui components and add them to the table.
     *
     * @param table components are added to this
     * @param skin  the visual appearance of all components
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
        //adds buttons to table, + fixing size on cells and spacing the table
        float spacing = 20f * Gdx.graphics.getDensity();
        float buttonHeight = 48f * Gdx.graphics.getDensity();
        float buttonWidth = 144f * Gdx.graphics.getDensity();

        //set textsize in labels
        float textSize = 4f;
        float smallerTextSize = 3f;
        titleLabel.setFontScale(textSize);
        volumeMusicLabel.setFontScale(smallerTextSize);
        volumeSoundLabel.setFontScale(smallerTextSize);
        musicOnOffLabel.setFontScale(smallerTextSize);
        soundOnOffLabel.setFontScale(smallerTextSize);
        backButton.getLabel().setFontScale(smallerTextSize);

        //TITLE
        table.add(titleLabel).colspan(2)
                .padBottom(spacing);


        //music slider
        /*table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);*/
        table.row();
        table.add(volumeMusicLabel).left();
        table.add(); //add for extra column spacing
        table.add(musicVolumeSlider)
                /*.padTop(50f)         //quickfix for centering the box
                .padBottom(spacing);*/
                .size(200, 100);

        //MUSICLABEL and checkbox
        /*table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);*/
        table.row();
        table.add(musicOnOffLabel).left();
        table.add(); //add for extra column spacing
        table.add(musicCheckbox)
                /*.padTop(50f)         //quickfix for centering the box
                .padBottom(spacing);*/
                .size(100, 100);
        musicCheckbox.getImage().setScaling(Scaling.fit);
        musicCheckbox.getImageCell().size(32 * Gdx.graphics.getDensity());

        //sound slider
        /*table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);*/
        table.row();
        table.add(volumeSoundLabel).left();
        table.add(); //add for extra column spacing
        table.add(soundVolumeSlider)
                /*.padTop(50f)         //quickfix for centering the box
                .padBottom(spacing)*/
                .size(200, 100);
        soundVolumeSlider.getStyle().knob.setMinWidth(40);
        soundVolumeSlider.getStyle().knob.setMinHeight(40);

        //SOUNDLABEL and checkbox.
        /*table.row().pad(TOP_SPAN, 0, 0, TOP_SPAN);*/
        table.row();
        table.add(soundOnOffLabel).left();
        table.add(); //add for extra column spacing
        table.add(soundEffectsCheckbox)
                .size(100, 100);
        //sizing of checkbox
        soundEffectsCheckbox.getImage().setScaling(Scaling.fit);
        soundEffectsCheckbox.getImageCell().size(32 * Gdx.graphics.getDensity());

        //backbutton
        //colspan fixes indentation
        table.row();
        table.add(backButton).colspan(3)
                .prefHeight(buttonHeight)
                .prefWidth(buttonWidth);
    }

    /**
     * @param delta
     */
    @Override
    public void render(final float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

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
