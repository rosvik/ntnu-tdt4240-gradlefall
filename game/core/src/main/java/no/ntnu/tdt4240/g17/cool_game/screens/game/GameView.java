package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.ntnu.tdt4240.g17.cool_game.character.TestMovementAPI;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.ControllerServerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.ControllerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.ControllerSystem;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.ProjectileComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.server.ServerSystem;
import no.ntnu.tdt4240.g17.cool_game.screens.game.server.ServerComponent;

/**
 * The MVC View for an active game.
 */

public class GameView implements Screen {

    /** Batch to render. */
    SpriteBatch batch;
    /** Arena. */
    private Arena arena;
    /** Arena width in tiles.*/
    private float width;
    /** Arena height in tiles. */
    private float height;
    /** Background image. */
    private Texture background;
    /** Textureatlas. */
    private TextureAtlas dungeonTilset;
    private TextureAtlas projectiles;
    private ControllerServerComponent controllerServerComponent;
    private Engine engine;
    /** Entities. */
    private Entity player1;
    private Entity player2;
    private Entity player3;
    private Entity player4;
    private Entity controller;
    /** Asset manager. */
    private AssetManager assetManager;
    private Boolean loadingComplete;
    private HeadsUpDisplay headsUpDisplay;

    /**
     * TODO: PATCH til server
     * TODO: GET fra server
     * TODO: Testing
     * TODO: JAVADOC
     * TODO: Checkstyle.
     */
    @Override
    public void show() {
        assetManager = new AssetManager();
        engine = new Engine();
        width = 32f;
        height = 20f;
        batch = new SpriteBatch();
        loadingComplete = false;
        controllerServerComponent = new ControllerServerComponent();
        // Creates entities
        player1 = new Entity();
        player2 = new Entity();
        player3 = new Entity();
        player4 = new Entity();
        controller = new Entity();
        //Add entities to engine
        engine.addEntity(player1);
        engine.addEntity(player2);
        engine.addEntity(player3);
        engine.addEntity(player4);
        engine.addEntity(controller);
        engine.addSystem(new ControllerSystem());
        engine.addSystem(new ServerSystem());
        //Load assets
        loadAssets();
    }

    /**
     * Load all assets that wil be used in the gameView.
     */
    private void loadAssets() {
        arena = new Arena("map2.tmx", 16f, width, height, batch);
        background = new Texture("background.png");
        assetManager.load("Assets/TextureAtlas/Characters/DungeonTileset.atlas", TextureAtlas.class);
        assetManager.load("Assets/TextureAtlas/Projectiles/Projectiles.atlas", TextureAtlas.class);
        System.out.println("LOADING ASSETS... " + assetManager.getProgress());
        setAssets();
    }

    /**
     * Add assets to components when assetManager is finished loading.
     */
    private void setAssets() {
        if (assetManager.isFinished()) {
            dungeonTilset = assetManager.get("Assets/TextureAtlas/Characters/DungeonTileset.atlas");
            projectiles = assetManager.get("Assets/TextureAtlas/Projectiles/Projectiles.atlas");
            //Add playerComponent
            player1.add(new PlayerComponent(0, 15, 10, "wizzard_f", dungeonTilset));
            player2.add(new PlayerComponent(1, 50, 10, "knight_m",  dungeonTilset));
            player3.add(new PlayerComponent(3, 15, 2, "big_zombie", dungeonTilset));
            player4.add(new PlayerComponent(4, 10, 10, "necromancer", dungeonTilset));
            //Add serverPosistion
            player1.add(new ServerComponent(new TestMovementAPI(), null));
            player2.add(new ServerComponent(new TestMovementAPI(), null));
            player3.add(new ServerComponent(new TestMovementAPI(), null));
            player4.add(new ServerComponent(null, controllerServerComponent));
            //Add projectiles
            player1.add(new ProjectileComponent("arrow", projectiles, 135));
            player2.add(new ProjectileComponent("arrow", projectiles, 135));
            player3.add(new ProjectileComponent("arrow", projectiles, 135));
            player4.add(new ProjectileComponent("arrow", projectiles, 135));
            //player4.add(new ProjectileComponent(3, "sword", projectiles, 270));
            //Add controllerComponent
            player4.add(ControllerComponent.getInstance());
            //Add Components to controller
            controller.add(ControllerComponent.getInstance());
            controller.add(controllerServerComponent);
            headsUpDisplay = new HeadsUpDisplay(dungeonTilset, projectiles);
            loadingComplete = true;
            System.out.println("LOADING COMPLETE");
        }
    }

    /**
     * Renders the game view.
     * @param delta = time since start.
     */
    @Override
    public void render(final float delta) {
        if (assetManager.update() && loadingComplete) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            arena.renderArena();

            // Draw the background
            batch.draw(background, 0f, 0f, width, height);

            // Render Arena
            arena.renderArena();

            // Draw all players
            player1.getComponent(PlayerComponent.class).getCharacter().draw(batch, delta);
            player2.getComponent(PlayerComponent.class).getCharacter().draw(batch, delta);
            player3.getComponent(PlayerComponent.class).getCharacter().draw(batch, delta);
            player4.getComponent(PlayerComponent.class).getCharacter().draw(batch, delta);
            // Draw all projectiles
            player1.getComponent(ProjectileComponent.class).drawProjectiles(batch);
            player2.getComponent(ProjectileComponent.class).drawProjectiles(batch);
            player3.getComponent(ProjectileComponent.class).drawProjectiles(batch);
            player4.getComponent(ProjectileComponent.class).drawProjectiles(batch);
            // Draw the foreground
            arena.renderForeground();
            headsUpDisplay.draw(batch,
                    player1.getComponent(PlayerComponent.class).getCharacter().getState().getLives(),
                    player4.getComponent(ProjectileComponent.class).getNumberOfProectiles(),
                    0);
            batch.end();
            engine.update(delta);
        } else {
            Gdx.gl.glClearColor(1, 0, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            setAssets();
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
