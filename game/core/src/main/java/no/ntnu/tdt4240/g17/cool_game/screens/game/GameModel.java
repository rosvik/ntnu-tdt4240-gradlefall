package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerSystem;
import no.ntnu.tdt4240.g17.cool_game.screens.game.projectile.ProjectileComponent;

/** Model for the GameView. */
@Getter
@Slf4j
public final class GameModel {

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
    /** Asset manager. */
    AssetManager assetManager;
    /** Tilset path. */
    static String dungeonTilesetPath = "Assets/TextureAtlas/Characters/DungeonTileset.atlas";
    static String projectileTilesetPath = "Assets/TextureAtlas/Projectiles/Projectiles.atlas";
    static String loadingImgPath = "Loading.png";
    static String backgroundImgPath = "background.png";
    private ClientData clientData;

    /** Create a new instance of the model. */
    public GameModel() {
        assetManager = new AssetManager();
        engine = new Engine();
        engine.removeAllEntities();
        players = new ArrayList<>();
        clientData = new ClientData();
        characters.add("knight_m");
        characters.add("wizzard_f");
        characters.add("big_zombie");
        characters.add("necromancer");
        engine.addSystem(new PlayerSystem());
        //engine.addSystem(new ProjectileSystem());
        loadAssets();
    }

    /**
     * Load all assets that wil be used in the gameView.
     */
    public void loadAssets() {
        assetManager.load("background.png", Texture.class);
        assetManager.load("Loading.png", Texture.class);
        assetManager.load(dungeonTilesetPath, TextureAtlas.class);
        assetManager.load(projectileTilesetPath, TextureAtlas.class);
        blockUntilLoadingComplete();
    }

    /**
     * Add assets to components when assetManager is finished loading.
     */
    public void blockUntilLoadingComplete() {
        log.debug("Loading assets...");
        assetManager.finishLoading();
        if (assetManager.update()) {
            //&& clientData.getMatchmadePlayers() != null) {
            dungeonTilset = assetManager.get(dungeonTilesetPath);
            projectilesTileset = assetManager.get(projectileTilesetPath);
            background = assetManager.get(backgroundImgPath);
            loading = assetManager.get(loadingImgPath);

            //for (int i = 0; i < clientData.getMatchmadePlayers().size(); i++) {
            for (int i = 0; i < 1; i++) {
                players.add(new Entity());
                engine.addEntity(players.get(i));
                Position pos = new Position();
                pos.x = 10;
                pos.y = 10;
                // Add player component
                players.get(i).add(
                        new PlayerComponent("" + i,
                                //clientData.getMatchmadePlayers().get(i).playerId,
                                pos,
                                //clientData.getMatchmadePlayers().get(i).position,
                                characters.get(i % 4),
                                dungeonTilset));
            }
            log.debug("Asset loading complete!");
        }
        log.trace("Asset loading progress: {}", assetManager.getProgress());
    }

    /** Update animations and render all players onto the batch.
     * @param deltaTime advance animations by this many seconds
     * @param batch sprites are rendered to this batch.
     */
    public void renderPlayers(final float deltaTime, final SpriteBatch batch) {
        for (final Entity player : players) {
            final GameCharacter character = PlayerComponent.MAPPER.get(player).getCharacter();
            character.update(deltaTime);
            character.draw(batch);
        }
    }

    /**
     * Render all projectile onto the batch.
     * @param batch sprites are rendered to this batch.
     */
    public void renderProjectiles(final SpriteBatch batch) {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).getComponent(ProjectileComponent.class).draw(batch);
        }
    }


    /**
     * If clientdata contains more projectile than screen,
     * screen creates a new entity (projectile) and append it to engine.
     * If cliendata conatins fewer projectiles than screen,
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

}
