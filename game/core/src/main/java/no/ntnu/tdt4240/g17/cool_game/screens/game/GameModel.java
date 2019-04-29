package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import lombok.Getter;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.network.ClientData;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerComponent;
import no.ntnu.tdt4240.g17.cool_game.screens.game.player.PlayerSystem;

import java.util.ArrayList;

@Getter
public class GameModel {

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
    private SpriteBatch batch;

    public GameModel(final SpriteBatch batch) {
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
        this.batch = batch;
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
        System.out.println("LOADING ASSETS.. ");
        setAssets();
    }

    /**
     * Add assets to components when assetManager is finished loading.
     */
    public void setAssets() {
        System.out.println("LOADING ASSETS...");
        assetManager.finishLoading();
        if (assetManager.update()) {
            System.out.println("YAY");
            //&& clientData.getMatchmadePlayers() != null) {
            dungeonTilset = assetManager.get(dungeonTilesetPath);
            projectilesTileset = assetManager.get(projectileTilesetPath);
            background = assetManager.get(backgroundImgPath);
            loading = assetManager.get(loadingImgPath);

            //for (int i = 0; i < clientData.getMatchmadePlayers().size(); i++) {
            for (int i = 0; i < 2; i++) {
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
            System.out.println("LOADING COMPLETE");
        }
        System.out.println("PROGRESS: " + assetManager.getProgress());
    }

    public void renderPlayers(final float deltaTime) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getComponent(PlayerComponent.class).getCharacter().draw(batch, deltaTime);
        }
    }

}
