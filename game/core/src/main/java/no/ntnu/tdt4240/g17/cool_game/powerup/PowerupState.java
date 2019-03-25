package no.ntnu.tdt4240.g17.cool_game.powerup;

/**
 * The state of a power-up.
 */
public class PowerupState {
    private float xPosition;
    private float yPosition;
    private final float baseAngle;
    private float directionAngle;
    private float height;
    private float width;



    /**
     * @param xPosition = start x position to projectile
     * @param yPosition = start y position to projectile
     * @param baseAngle = the angle where the projectile is pointing left (90 deg)
     * @param height = the height of the sprite
     * @param width = the width of the sprite
     */
    public PowerupState(final int xPosition,
                           final int yPosition,
                           final float baseAngle,
                           final float height,
                           final float width) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.baseAngle = baseAngle;
        this.directionAngle = 0;
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
     * @return the directionalAngle
     */
    public float getDirectionAngle() {
        return directionAngle;
    }

    /**
     * @return the angle of the projectile
     */
    public float getAngle() {
        return baseAngle + directionAngle;
    }

    /**
     * @param newAngle sets a new directional angle if it is not 0.
     *                  if it is 0, the angle has not change.
     */
    public void setDirectionAngle(final float newAngle) {
        if (newAngle != 0) {
            this.directionAngle = newAngle;
        }
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
