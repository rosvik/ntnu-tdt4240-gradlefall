package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;

/**
 * The MVC View for an active game.
 */

public class GameView implements Screen {
    SpriteBatch batch;
    static String dungeonTilesetPath = "Assets/TextureAtlas/Characters/DungeonTileset.atlas";
    static String projectileTilesetPath = "Assets/TextureAtlas/Projectiles/Projectiles.atlas";
    private GameModel model;
    private Arena arena;
    private HeadsUpDisplay hud;
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
        //hud = new HeadsUpDisplay(model.getDungeonTilset(), model.getProjectilesTileset());
    }



    @Override
    public void render(final float delta) {
        //if (assetManager.update() && loadingComplete) {
        if (model.assetManager.update()) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(model.getBackground(), 0f, 0f, 32, 20);
            arena.renderArena();
            model.renderPlayers(delta);
            arena.renderForeground();
            //hud.draw(batch,3,10,0);
            model.getEngine().update(delta);
            batch.end();
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
