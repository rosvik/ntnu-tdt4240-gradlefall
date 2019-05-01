package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.network.NetworkSettings;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.GUI;
import no.ntnu.tdt4240.g17.cool_game.screens.navigation.Navigator;

/**
 * The MVC View for an active game.
 */
@Slf4j
public final class GameView implements Screen {
    SpriteBatch batch;
    private final Navigator navigator;
    private GameModel model;
    private Arena arena;
    private HeadsUpDisplay hud;
    private Consumer<ControlsMessage> controlsMessageNetworkSender;
    private boolean isGameOver = false;

    /**
     * Constructor for game view.
     *
     * @param batch = The batch that everything will render on.
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
        controlsMessageNetworkSender = GameClient.getNetworkClientInstance()::sendTCP;

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
        }
        //if (model.assetManager.update() && model.getClientData().getMatchmadePlayers().size() == 4
        // && controlsMessageNetworkSender.getClient().isConnected()) {
        if (model.assetManager.update()) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(model.getBackground(), 0f, 0f, 32, 20);
            arena.renderArena();
            model.renderPlayers(delta, batch);
            arena.renderForeground();
            hud.draw(batch, 3, 10, 0);
            model.getEngine().update(delta);
            batch.end();
            final ControlsMessage controlsMessage = GUI.getInstance().update();
            controlsMessageNetworkSender.accept(controlsMessage);
        } else {
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
    }
}
