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
import java.util.ArrayList;

/**
 * The MVC View for an active game.
 */

public class GameView implements Screen {

    /** Batch to render. */
    SpriteBatch batch;
    /** Number of players. */
    private int numberOfPlayers;
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
    /** Characters. */
    private ArrayList<String> characters = new ArrayList();
    /** Components. */
    private ControllerServerComponent controllerServerComponent;
    /** Engine */
    private Engine engine;
    /** Entities. */
    private ArrayList<Entity> players;
    private Entity player1;
    private Entity player2;
    private Entity player3;
    private Entity player4;
    private Entity controller;
    /** Asset manager. */
    AssetManager assetManager;
    private Boolean loadingComplete;
    private HeadsUpDisplay headsUpDisplay;
    static String dungeonTileset = "Assets/TextureAtlas/Characters/DungeonTileset.atlas";
    static String projectileTileset = "Assets/TextureAtlas/Projectiles/Projectiles.atlas";


    public GameView(final int numberOfPlayers, final SpriteBatch batch) {
        this.numberOfPlayers = numberOfPlayers;
        this.batch = batch;
    }

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
        loadingComplete = false;
        controllerServerComponent = new ControllerServerComponent();
        players = new ArrayList<>();

        characters.add("knight_m");
        characters.add("wizzard_f");
        characters.add("big_zombie");
        characters.add("necromancer");
        // Creates entities
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Entity());
            engine.addEntity(players.get(i));
        }
        controller = new Entity();
        engine.addEntity(controller);
        engine.addSystem(new ControllerSystem());
        engine.addSystem(new ServerSystem());
        // Load assets
        loadAssets();
    }

    /**
     * Load all assets that wil be used in the gameView.
     */
    private void loadAssets() {
        arena = new Arena("map2.tmx", 16f, width, height, batch);
        background = new Texture("background.png");
        assetManager.load(dungeonTileset, TextureAtlas.class);
        assetManager.load(projectileTileset, TextureAtlas.class);
        System.out.println("LOADING ASSETS... " + assetManager.getProgress());
        setAssets();
    }

    /**
     * Add assets to components when assetManager is finished loading.
     */
    private void setAssets() {
        if (assetManager.isFinished()) {
            dungeonTilset = assetManager.get(dungeonTileset);
            projectiles = assetManager.get(projectileTileset);
            for (int i = 0; i < players.size(); i++) {
                // Add player component
                players.get(i).add(new PlayerComponent(0, 15, 10, characters.get(i % 4), dungeonTilset));
                // Add projectile component
                players.get(i).add(new ProjectileComponent("arrow", projectiles, 135));
                //players.get(i).add(new ProjectileComponent("sword", projectiles, 270));
                // Add server component
                if (i != 0) {
                    players.get(i).add(new ServerComponent(new TestMovementAPI(), null));
                } else {
                    players.get(i).add(new ServerComponent(null, controllerServerComponent));
                }
            }
            // Add controllerComponent.
            players.get(0).add(ControllerComponent.getInstance());
            // Add Components to controller
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

            // Draw all players and projectiles
            for (int i = 0; i < players.size(); i++) {
                players.get(i).getComponent(PlayerComponent.class).getCharacter().draw(batch, delta);
                players.get(i).getComponent(ProjectileComponent.class).drawProjectiles(batch);
            }
            // Draw the foreground
            arena.renderForeground();
            headsUpDisplay.draw(batch,
                    players.get(0).getComponent(PlayerComponent.class).getCharacter().getState().getLives(),
                    players.get(0).getComponent(ProjectileComponent.class).getNumberOfProectiles(),
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
