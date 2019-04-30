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
import com.esotericsoftware.kryonet.Client;

import java.util.ArrayList;

import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.ControllerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.ControllerSystem;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerSystem;
import no.ntnu.tdt4240.g17.cool_game.screens.game.projectile.ProjectileComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.projectile.ProjectileSystem;

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
    /** Loading image. */
    private Texture loading;
    /** Textureatlas. */
    private TextureAtlas dungeonTilset;
    private TextureAtlas projectilesTileset;
    /** Characters. */
    private ArrayList<String> characters = new ArrayList();
    /** Engine. */
    private Engine engine;
    /** Entities. */
    private ArrayList<Entity> players;
    private ArrayList<Entity> projectiles;
    private Entity controller;
    /** Asset manager. */
    AssetManager assetManager;
    private Boolean loadingComplete;
    private HeadsUpDisplay headsUpDisplay;
    /** Tilset path. */
    static String dungeonTilesetPath = "Assets/TextureAtlas/Characters/DungeonTileset.atlas";
    static String projectileTilesetPath = "Assets/TextureAtlas/Projectiles/Projectiles.atlas";
    private ClientData clientData;
    /**
     * Constructor for game view.
     * @param batch = The batch that everything will render on.
     */
    public GameView(final SpriteBatch batch) {
        this.batch = batch;
    }

    /**
     * Creates items in screen.
     * todo: Add projectile render support
     */
    @Override
    public void show() {
        assetManager = new AssetManager();
        engine = new Engine();
        width = 32f;
        height = 20f;
        loadingComplete = false;
        players = new ArrayList<>();
        clientData = new ClientData();
        characters.add("knight_m");
        characters.add("wizzard_f");
        characters.add("big_zombie");
        characters.add("necromancer");
        controller = new Entity();
        engine.addEntity(controller);
        final ControllerSystem system = new ControllerSystem();
        final Client networkClient = GameClient.getNetworkClientInstance();
        system.setServerConnection(networkClient::sendTCP);
        engine.addSystem(system);
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new ProjectileSystem());
        // Load assets
        loadAssets();
    }

    /**
     * Load all assets that wil be used in the gameView.
     */
    private void loadAssets() {
        arena = new Arena("map2.tmx", 16f, width, height, batch);
        background = new Texture("background.png");
        loading = new Texture("Loading.png");
        assetManager.load(dungeonTilesetPath, TextureAtlas.class);
        assetManager.load(projectileTilesetPath, TextureAtlas.class);
        System.out.println("LOADING ASSETS... ");
        setAssets();
    }

    /**
     * If clientdata contains more projectile than screen,
     * screen creates a new entity (projectile) and append it to engine.
     * If cliendata conatins fewere projectiles than screen,
     * screen removes the entity froms screen and engine
     */
    public void updateProjectiles() {
        if (clientData.getProjectiles().size() > projectiles.size()) {
            for (int i = 0; i < clientData.getProjectiles().size(); i++) {
                boolean isInList = false;
                for (int j = 0; j < projectiles.size(); j++) {
                    Entity entity = projectiles.get(j);
                    String projectileComponentId = entity.getComponent(ProjectileComponent.class).getProjectileId();
                    if (clientData.getProjectiles().get(i).projectileId.equals(projectileComponentId)) {
                        isInList = true;
                        break;
                    }
                }
                if (!isInList) {
                    Entity newProjectile = new Entity();
                    engine.addEntity(newProjectile);
                    newProjectile.add(new ProjectileComponent(
                            clientData.getProjectiles().get(i).projectileId,
                            clientData.getProjectiles().get(i).projectilePosition,
                            "arrow",
                            135,
                            projectilesTileset));
                    projectiles.add(newProjectile);
                    if (clientData.getProjectiles().size() == projectiles.size()) {
                        break;
                    }
                }
            }
        } else if (clientData.getProjectiles().size() < projectiles.size()) {
            for (int i = 0; i < projectiles.size(); i++) {
                boolean isInList = false;
                String id = projectiles.get(i).getComponent(ProjectileComponent.class).getProjectileId();
                for (int j = 0; j < clientData.getProjectiles().size(); j++) {
                   if (clientData.getProjectiles().get(j).projectileId.equals(id)) {
                       isInList = true;
                       break;
                   }
                }
                if (!isInList) {
                    engine.removeEntity(projectiles.get(i));
                    projectiles.remove(i);
                    if (clientData.getProjectiles().size() == projectiles.size()) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Add assets to components when assetManager is finished loading.
     */
    private void setAssets() {
        if (assetManager.isFinished() && clientData.getMatchmadePlayers() != null) {
            dungeonTilset = assetManager.get(dungeonTilesetPath);
            projectilesTileset = assetManager.get(projectileTilesetPath);
            for (int i = 0; i < clientData.getMatchmadePlayers().size(); i++) {
                players.add(new Entity());
                engine.addEntity(players.get(i));
                // Add player component
                players.get(i).add(
                        new PlayerComponent(clientData.getMatchmadePlayers().get(i).playerId,
                                clientData.getMatchmadePlayers().get(i).position,
                                characters.get(i % 4),
                                dungeonTilset));
            }
            // Add Components to controller
            controller.add(ControllerComponent.getInstance());
            headsUpDisplay = new HeadsUpDisplay(dungeonTilset, projectilesTileset);
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
            if (projectiles.size() != clientData.getProjectiles().size()) {
                updateProjectiles();
            }
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            arena.renderArena();
            // Draw the background
            batch.draw(background, 0f, 0f, width, height);
            // Render Arena
            arena.renderArena();
            // Draw all players
            for (int i = 0; i < clientData.getUpdatePlayers().size(); i++) {
                players.get(i).getComponent(PlayerComponent.class).getCharacter().draw(batch, delta);
            }
            // Draw all projectiles
            for (int i = 0; i < projectiles.size(); i++) {
                projectiles.get(i).getComponent(ProjectileComponent.class).draw(batch);
            }
            // Draw the foreground
            arena.renderForeground();
            //todo: Assign hud to player
            headsUpDisplay.draw(batch,
                    3, //players.get(0).getComponent(PlayerComponent.class).getCharacter().getState().getLives(),
                    5, //players.get(0).getComponent(ProjectileComponent.class).getNumberOfProectiles(),
                    0);
            batch.end();
            engine.update(delta);
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(loading, 0f, 0f, width, height);
            batch.end();
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
