package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.network.NetworkSettings;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.GUI;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.UserInputButtons;
import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * The MVC View for an active game.
 */
@Slf4j
public final class GameView implements Screen {
    private final BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("font/arial.fnt"));
    private UserInputButtons userInputButtons;
    SpriteBatch batch;
    private final Navigator navigator;
    private GameModel model;
    private Arena arena;
    private HeadsUpDisplay hud;
    private boolean isGameOver = false;
    private ShapeRenderer shapeRenderer;

    /**
     * Constructor for game view.
     *
     * @param batch     = The batch that everything will render on.
     * @param navigator Navigator to change screens
     */
    public GameView(final SpriteBatch batch, final Navigator navigator) {
        this.batch = batch;
        this.navigator = navigator;
    }

    @Override
    public void show() {
        arena = new Arena("map2.tmx", 16f, 32, 20, batch);
        final GameClient gameClient = new GameClient(NetworkSettings.getServerIp(),
                NetworkSettings.getPort(), ClientData.getInstance());
        model = new GameModel(ClientData.getInstance());
        hud = new HeadsUpDisplay(model.getDungeonTilset(), model.getProjectilesTileset());
        shapeRenderer = new ShapeRenderer();
        userInputButtons = GUI.getInstance().getInputProcessor().getUserInputButtons();

        final Client networkClientInstance = GameClient.getNetworkClientInstance();
        networkClientInstance.addListener(new Listener() {
            @Override
            public void disconnected(final Connection connection) {
                // FIXME: 5/1/2019 Add reconnecting etc.
                log.warn("Disconnected from server!");
                networkClientInstance.removeListener(this);
                isGameOver = true;
            }
        });
    }


    @Override
    public void render(final float delta) {
        if (isGameOver) {
            // Also happens when disconnected from server.
            navigator.changeView(Navigator.Screen.HOME);
            return;
        }
        //if (model.assetManager.update() && model.getClientData().getMatchmadePlayers().size() == 4
        // && controlsMessageNetworkSender.getClient().isConnected()) {
        if (model.assetManager.update()) {
            // Draw the game when assets are loaded
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            batch.draw(model.getBackground(), 0f, 0f, 32, 20);
            arena.renderArena();
            model.renderPlayers(delta, batch);
            arena.renderForeground();
            hud.draw(batch, 3, 10, 0);
            batch.end();

            // Draw input controls
            Gdx.gl.glEnable(GL20.GL_BLEND); // Enable transparent rendering
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Enable transparent rendering
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            final Circle joystick = userInputButtons.getJoystick();
            shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 0.4f);
            shapeRenderer.circle(joystick.x, joystick.y, joystick.radius);
            final Rectangle jump = userInputButtons.getJump();
            shapeRenderer.box(jump.x, jump.y, 0, jump.width, jump.height, 0);
            final boolean showShootButton = false;
            final boolean showPlaceButton = false;
            if (showShootButton) {
                final Rectangle shoot = userInputButtons.getShoot();
                shapeRenderer.box(shoot.x, shoot.y, 0, shoot.width, shoot.height, 0);
            }
            if (showPlaceButton) {
                final Rectangle placeBlock = userInputButtons.getPlace();
                shapeRenderer.box(placeBlock.x, placeBlock.y, 0, placeBlock.width, placeBlock.height, 0);
            }
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND); // Disable transparent rendering

            model.getEngine().update(delta);
        } else {
            // Draw loading view when assets are loading
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(model.getLoading(), 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }
    }

    /**
     * @param newWidth  = new width
     * @param newHeight = new height
     */
    @Override
    public void resize(final int newWidth, final int newHeight) {

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
     * Disposes the batch.
     */
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        bitmapFont.dispose();
    }
}
