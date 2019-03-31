package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.ntnu.tdt4240.g17.cool_game.projectile.Projectile;
import java.util.ArrayList;

/**
 * Component for projectile.
 * @author Håvard 'havfar' Farestveit
 */
public class ProjectileComponent implements Component {
    private int numberOfProectiles = 10;
    private TextureAtlas atlas;
    private String name;
    private ArrayList<Projectile> firedProjectiles =  new ArrayList<>();
    private float baseAngle;

    /**
     * Constructor.
     * @param projectileName = Name in texture atlas
     * @param projectiles = texture
     * @param baseAngle = the angle the texture has when going flat right
     */
    public ProjectileComponent(final String projectileName, final TextureAtlas projectiles, final float baseAngle) {
        name = projectileName;
        atlas = projectiles;
        this.baseAngle = baseAngle;
    }

    /**
     * @param x = start position for projectile
     * @param y = start position for projectile
     * @param direction = movement direction for projectile
     */
    public void shoot(final float x, final float y, final float direction) {
        if (numberOfProectiles > 0) {
            firedProjectiles.add(new Projectile(name, x, y, this.baseAngle, direction, atlas));
            numberOfProectiles -= 1;
        }
    }

    /**
     * increases number of porjectiles with one.
     */
    public void incrementProjectiles() {
        numberOfProectiles += 1;
    }

    /**
     * @return number of projectiles
     */
    public int getNumberOfProectiles() {
        return numberOfProectiles;
    }

    /**
     * Draws all fired projectiles to batch.
     * @param batch = Spritebatch to draw on
     */
    public void drawProjectiles(final SpriteBatch batch) {
        for (int i = 0; i < firedProjectiles.size(); i++) {
            firedProjectiles.get(i).draw(batch);
        }
    }

    /**
     * Render all fired projectiles.
     */
    public void renderProjectiles() {
        for (int i = 0; i < firedProjectiles.size(); i++) {
            firedProjectiles.get(i).testRender();
        }
    }


}
