package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.GUI;

/**
 * The MVC View for an active game.
 */
@Slf4j
public class GameView implements Screen {
    SpriteBatch batch;
    private GameModel model;
    private Arena arena;
    private HeadsUpDisplay hud;
    private GameClient client;
    /**
     * Constructor for game view.
     * @param batch = The batch that everything will render on.
     */
    public GameView(final SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        arena = new Arena("map2.tmx", 16f, 32, 20, batch);
        model = new GameModel(batch);
        hud = new HeadsUpDisplay(model.getDungeonTilset(), model.getProjectilesTileset());
        //client = new GameClient(5777, new ClientData());
        //client.run();
        //log.info("Is connected: {}", client.getClient().isConnected());
    }




    @Override
    public void render(final float delta) {
        //if (model.assetManager.update() && model.getClientData().getMatchmadePlayers().size() == 4 && client.getClient().isConnected()) {
        if (model.assetManager.update()) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(model.getBackground(), 0f, 0f, 32, 20);
            arena.renderArena();
            model.renderPlayers(delta);
            arena.renderForeground();
            hud.draw(batch,3,10,0);
            model.getEngine().update(delta);
            batch.end();
            GUI.getInstance().update();
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(model.getLoading(), 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }
    }

    /**
     * @param newWidth = new width
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
