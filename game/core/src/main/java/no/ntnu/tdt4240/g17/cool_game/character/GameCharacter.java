package no.ntnu.tdt4240.g17.cool_game.character;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.extern.slf4j.Slf4j;


/**
 * How to add new character.
 * atlas = new TextureAtlas("DungeonTileset.atlas");
 * character = new GameCharacter("big_zombie", 100,  100, atlas);
 *
 * Then in render() in mainGames.
 * character.render(new X Value, new Y Value);
 * character.draw(spritebatch from maingame, stateTime from maingame)
 */
@Slf4j
public class GameCharacter {
    private GameCharacterState state;
    private GameCharacterAnimation animation;

    /**
     * Constructor.
     * @param name = character name, must be the same as the character animation in DungeonTileset.atlas.
     * @param xPosition = The initial starting x position for the character
     * @param yPosition = The initial starting y position for the character
     * @param atlas = TextureAtlas with animation sprites
     */
    public GameCharacter(final String name, final int xPosition, final int yPosition, final TextureAtlas atlas) {
        this.state = new GameCharacterState(xPosition, yPosition);
        this.animation = new GameCharacterAnimation(name, atlas);
    }

    /**
     * @return the characters animation
     */
    public GameCharacterAnimation getAnimation() {
        return this.animation;
    }

    /**
     * Render the character.
     * Changes the posistion and animation.
     * @param newX = the new x posistion
     * @param newY = the new y posistion
     */
    public void render(final int newX, final int newY) {
        if (newY > this.state.getyPosition()) {
            this.animation.jump();
        } else if (newY  < this.state.getyPosition()) {
            this.animation.fall();
        } else if (newX < this.state.getxPosition()) {
            this.animation.moveLeft();
        } else if (newX > this.state.getxPosition()) {
            this.animation.moveRigth();
        } else {
            this.animation.idle();
        }
        this.state.setxPosition(newX);
        this.state.setyPosition(newY);
    }

    /**
     * @param batch = function will draw the current frame to this spritebatch.
     * @param stateTime = time of game, chooses what frame will be shown
     */
    public void draw(final SpriteBatch batch, final float stateTime) {
        TextureRegion characterTextureRegion = this.animation.getFrame(stateTime);
        batch.draw(characterTextureRegion,
                this.state.getyPosition(),
                this.state.getxPosition(),
                this.animation.getWidth(),
                this.animation.getHeight());
    }
}
