package no.ntnu.tdt4240.g17.cool_game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
import no.ntnu.tdt4240.g17.cool_game.game_arena.Arena;
import no.ntnu.tdt4240.g17.cool_game.projectile.Projectile;
import no.ntnu.tdt4240.g17.cool_game.screens.game.GameView;

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

    /** Screens. */
    private GameView gameView;


    @Override
    public final void create() {
        gameView = new GameView(4, new SpriteBatch());
        gameView.show();
    }

    @Override
    public final void render() {
        gameView.render(stateTime);
        stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public final void dispose() {
        batch.dispose();
    }
}
