package no.ntnu.tdt4240.g17.cool_game.character;
import com.badlogic.gdx.graphics.g2d.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * A playable game character.
 */
public class GameCharacter {

    private String name;

    private GameCharacterState state;
    private GameCharacterAnimation animation;

    /**
     * Constructor.
     * @param name = character name, must be the same as the character animation in DungeonTilset.atals. e.g. wizzard_f, knigth_m, zombie...
     * @param xPosition = The inital starting x posistion for the character
     * @param yPosition = The inital starting y posistion for the character
     * @param atlas = Textureatlas whit animation sprites
     */
    public GameCharacter(final String name, final int xPosition, final int yPosition, final TextureAtlas atlas) {
        this.name = name;
        this.state = new GameCharacterState(xPosition, yPosition);
        this.animation = new GameCharacterAnimation(name, atlas);
    }

    /**

     */

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
     * @param batch = spritebatch, function will draw the current frame to this spritebatch.
     * @param stateTime = time of game, chooses what frame will be shown
     */
    public void draw(final SpriteBatch batch, final float stateTime) {
        TextureRegion characterTextureRegion = this.animation.getFrame(stateTime);
        batch.draw(characterTextureRegion, this.state.getxPosition(), this.state.getyPosition(), this.animation.getWidth(), this.animation.getHeight());
    }

    /**
     * @return toString
     */
    @Override
    public String toString() {
        return ("GameCharacter: "
                + this.name
                + ", Position: (" + this.state.getxPosition() + "," + this.state.getyPosition()
                + "), Animation: " + this.animation
                + ", Lives: " + this.state.getLives());
    }
}
