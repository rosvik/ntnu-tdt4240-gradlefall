package no.ntnu.tdt4240.g17.cool_game.powerup;

/**
 * The state of a power-up.
 */
public class PowerupState {
    private float xPosition;
    private float yPosition;
    private float height;
    private float width;



    /**
     * @param xPosition = start x position to projectile
     * @param yPosition = start y position to projectile
     * @param height = the height of the sprite
     * @param width = the width of the sprite
     */
    public PowerupState(final int xPosition,
                           final int yPosition,
                           final float height,
                           final float width) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
    }

    /**
     * @return the projectiles x position
     */
    public float getxPosition() {
        return xPosition;
    }

    /**
     * @param xPosition = the new x position
     */
    public void setxPosition(final float xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * @return the projectiles y position
     */
    public float getyPosition() {
        return yPosition;
    }

    /**
     * @param yPosition = the new y position
     */
    public void setyPosition(final float yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * @return the height of the projectile
     */
    public float getHeight() {
        return height;
    }

    /**
     * @return the width of the projectile
     */
    public float getWidth() {
        return width;
    }
}
