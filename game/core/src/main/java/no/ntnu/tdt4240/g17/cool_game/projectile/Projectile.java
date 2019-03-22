package no.ntnu.tdt4240.g17.cool_game.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A projectile.
 */
public class Projectile {
   private ProjectileState state;
   private TextureAtlas projectiles = new TextureAtlas("./Assets/Projectiles2.atlas");
   private TextureRegion projectile;

    /**
     * @param name = the name of the projectile as it is in projectile.atlas
     * @param xPosition = start x position to projectile
     * @param yPosition = start y position to projectile
     * @param baseAngle = the angle of the projectile in projectile.atlas
     */
   public Projectile(final String name, final int xPosition, final int yPosition, final float baseAngle) {
       float height = projectiles.findRegion(name).getTexture().getHeight();
       float width = projectiles.findRegion(name).getTexture().getWidth();
       this.state = new ProjectileState(xPosition, yPosition, baseAngle, height, width);
       this.projectile = projectiles.findRegion(name);
   }

    /**
     * Render the character.
     * Changes the position and animation.
     * @param newX = the new x position
     * @param newY = the new y position
     */
    public void render(final float newX, final float newY) {
        float newDirectionAngle = new Float(Math.atan2(newY - this.state.getyPosition(), newX - this.state.getxPosition())  * 180.0d / Math.PI);
        this.state.setDirectionAngle(newDirectionAngle);
        this.state.setxPosition(newX);
        this.state.setyPosition(newY);
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
        batch.draw(projectile,
                this.state.getxPosition(),
                this.state.getyPosition(),
                0,
                0,
                this.state.getWidth(),
                this.state.getHeight(),
                getScale(this.state.getWidth()),
                getScale(this.state.getHeight()),
                this.state.getAngle()
        );
    }

    /**
     * @return the current state.
     */
    public ProjectileState getState() {
        return state;
    }
}
