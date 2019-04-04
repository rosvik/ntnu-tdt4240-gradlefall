package no.ntnu.tdt4240.g17.cool_game.screens.game.projectile;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import lombok.Getter;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.cool_game.projectile.Projectile;

/**
 * lombok.
 */
@Getter
/*
 * Component class for projectile
 */
public class ProjectileComponent implements Component {
    private Projectile projectile;
    private String projectileId;

    /**
     * Constructor.
     * @param id = Unique id for player
     * @param position = object posistion
     * @param name = texture name
     * @param atlas = texture atalas
     * @param baseAngle = the angle the projectile has going stright right
     */
    public ProjectileComponent(
            final String id,
            final Position position,
            final String name,
            final float baseAngle,
            final TextureAtlas atlas) {
        projectileId = id;
        projectile = new Projectile(name, position.x, position.y, baseAngle, 0, atlas);
    }

    /**
     * Render the projectile.
     * @param x = new x position
     * @param y = new y position
     * @param angle = new angle
     */
    public void render(final float x, final float y, final float angle) {
        projectile.render(x, y, angle);
    }

    /**
     * Draw projectile to batch.
     * @param batch = sprite batch from GameView.
     */
    public void draw(final SpriteBatch batch) {
        projectile.draw(batch);
    }
}
