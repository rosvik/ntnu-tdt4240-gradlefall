package no.ntnu.tdt4240.g17.cool_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
import no.ntnu.tdt4240.g17.cool_game.character.TestMovementAPI;

/**
 * Main game class.
 */
public class MainGame extends ApplicationAdapter {
    /** Batch to render. */
    private SpriteBatch batch;
    private GameCharacter character1;
    private GameCharacter character2;
    private GameCharacter character3;
    private GameCharacter character4;
    private TextureAtlas atlas;
    private TestMovementAPI api1;
    private TestMovementAPI api2;
    private TestMovementAPI api3;
    private TestMovementAPI api4;
    private float stateTime;

    @Override
    public final void create() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas("DungeonTilset.atlas");
        character1 = new GameCharacter("big_zombie", 100,  100, atlas);
        character2 = new GameCharacter("wizzard_m", 200, 100, atlas);
        character3 = new GameCharacter("knight_m", 300, 100, atlas);
        character4 = new GameCharacter("big_demon", 400, 100, atlas);
        stateTime = 0;

        this.api1 = new TestMovementAPI();
        this.api2 = new TestMovementAPI();
        this.api3 = new TestMovementAPI();
        this.api4 = new TestMovementAPI();

    }

    @Override
    public final void render() {
        this.api1.changeXvalue();
        this.api2.changeXvalue();
        this.api3.changeXvalue();
        this.api4.changeXvalue();

        character1.render(api1.getxValue(), api1.getyValue());
        character2.render(api2.getxValue(), api2.getyValue());
        character3.render(api3.getxValue(), api3.getyValue());
        character4.render(api4.getxValue(), api4.getyValue());

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        character1.draw(batch, stateTime);
        character2.draw(batch, stateTime);
        character3.draw(batch, stateTime);
        character4.draw(batch, stateTime);
        batch.end();

    }

    @Override
    public final void dispose() {
        batch.dispose();
    }
}
