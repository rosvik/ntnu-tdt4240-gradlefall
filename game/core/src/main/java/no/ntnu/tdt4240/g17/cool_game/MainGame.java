package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.projectile.Projectile;

/**
 * Main game class.
 */
public class MainGame extends ApplicationAdapter {
    float stateTime;

    /** Batch to render. */
    SpriteBatch batch;
    /** Background image. */
    private Texture background;

    /** Arena. */
    Arena arena;

    /** GameCharacter. */
    GameCharacter character1;

    /** Textureatlas. */
    TextureAtlas dungeonTilset;
    TextureAtlas projectiles;

    // Projectile
    Projectile projectile;

    /** Arena width in tiles.*/
    private float width;

    /** Arena height in tiles. */
    private float height;


    @Override
    public final void create() {
        width = 32f;
        height = 20f;

        batch = new SpriteBatch();
        dungeonTilset = new TextureAtlas("Assets/TextureAtlas/Characters/DungeonTileset.atlas");
        projectiles = new TextureAtlas("Assets/TextureAtlas/Projectiles/Projectiles.atlas");
        character1 = new GameCharacter("knight_m", 10, 10, dungeonTilset, "arrow", projectiles);
        stateTime = 0;
        projectile = new Projectile("arrow", 0, 0, 135, projectiles);
        arena = new Arena("map2.tmx", 16f, width, height, batch);
        background = new Texture("background.png");
    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* character. */
        character1.render(10, 10);
        projectile.render(9, 10);

        batch.begin();
        arena.renderArena();

        // Draw the background
        batch.draw(background, 0f, 0f, width, height);

        // Render character and projectile.
        arena.renderArena();
        projectile.draw(batch);
        character1.draw(batch, stateTime);

        arena.renderForeground();
        batch.end();

        stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public final void dispose() {
        batch.dispose();
    }
}
