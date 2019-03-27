package no.ntnu.tdt4240.g17.cool_game.powerup;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A Power-up.
 */
public class Powerup {
    private PowerupState state;
    private TextureAtlas dungeonTileset;
    private TextureRegion powerup;
    private static final int TILE_SCALE_CONSTANT = 32;
    private static int movement;

    /**
     * @param name = the name of the powerup as it is in the TextureAtlas
     * @param xPosition = start x position to powerup
     * @param yPosition = start y position to powerup
     * @param powerupTexture = the TextureAtlas where the powerup sprite is.
     */
    public Powerup(final String name,
                      final int xPosition,
                      final int yPosition,
                      final TextureAtlas powerupTexture) {
        this.dungeonTileset = powerupTexture;
        float height = dungeonTileset.findRegion(name).getRegionHeight();
        float width = dungeonTileset.findRegion(name).getRegionWidth();
        this.state = new PowerupState(xPosition, yPosition, height, width);
        this.powerup = dungeonTileset.findRegion(name);
    }

    /**
     * Render the character.
     * Changes the position and animation.
     */
    public void render() {
        if (movement % 80 < 20) {
            this.state.setyPosition(this.state.getyPosition() + 0.007f);
            movement++;
        } else if (movement % 80 < 40) {
            this.state.setyPosition(this.state.getyPosition() - 0.007f);
            movement++;
        } else {
            movement++;
        }
    }

    /**
     * Scales the number to fit frame.
     * 24f is the tile frame size.
     * @param scaleNumber = the number you what to scale
     * @return how large the number should be  to fit the frame.
     */
    public float getScale(final float scaleNumber) {
        return 24f / scaleNumber;
    }

    /**
     * @param batch = function will draw the current frame to this spritebatch.
     */
    public void draw(final SpriteBatch batch) {
        batch.draw(powerup,
                this.state.getxPosition(),
                this.state.getyPosition(),
               0.7f,
                0.7f
        );
    }

    /**
     * @return the current state.
     */
    public PowerupState getState() {
        return state;
    }
}
