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
    private float animationStateTime = 0f;

    /**
     * Constructor.
     * @param name = character name, must be the same as the character animation in DungeonTileset.atlas.
     * @param xPosition = The initial starting x position for the character
     * @param yPosition = The initial starting y position for the character
     * @param atlas = TextureAtlas with animation sprites
     */
    public GameCharacter(final String name, final float xPosition, final float yPosition, final TextureAtlas atlas) {
        this.state = new GameCharacterState(xPosition, yPosition);
        this.animation = new GameCharacterAnimation(name, atlas);
    }

    /**
     * @return the character state.
     */
    public GameCharacterState getState() {
        return state;
    }

    /**
     * @return the caracters animation
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
    public void render(final float newX, final float newY) {
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
     * Updates the animation state.
     * @param deltaTime the amount of time to update the animation with.
     */
    public void update(final float deltaTime) {
        animationStateTime += deltaTime;
    }

    /**
     * @param batch = function will draw the current frame to this spritebatch.
     */
    public void draw(final SpriteBatch batch) {
        TextureRegion characterTextureRegion = this.animation.getFrame(animationStateTime);
        final float width = this.animation.getWidth();
        float x = this.state.getxPosition();
        if (width < 0) {
            // Compensate the flipping of sprites by adjusting left-side.
            // This keeps (0, 0) as bottom left corner of sprite.
            x -= width;
        }
        batch.draw(characterTextureRegion,
                x,
                this.state.getyPosition(),
                width,
                this.animation.getHeight());
    }
}
