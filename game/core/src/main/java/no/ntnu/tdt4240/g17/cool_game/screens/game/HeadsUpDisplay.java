package no.ntnu.tdt4240.g17.cool_game.screens.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Heads up display to show how many lives and projectiles left.
 * @author Håvard 'havfar' Farestveit
 */
public class HeadsUpDisplay {
    private TextureRegion heart;
    private TextureRegion projectile;

    /**
     * @param tileset1 = heart tileset.
     * @param tileset2 = projectile tileset.
     */
    public HeadsUpDisplay(final TextureAtlas tileset1, final TextureAtlas tileset2) {
        heart = tileset1.findRegion("ui_heart_full");
        projectile = tileset2.findRegion("arrow");
    }

    /**
     * draws the HUD.
     * @param batch = sprite batch from game view
     * @param numeberOfLives = current number of lives
     * @param numerOfProjectiles = current number of projectiles
     * @param playerScore = current score.
     *                    Todo: add score support
     */
    public void draw(
            final SpriteBatch batch,
            final int numeberOfLives,
            final int numerOfProjectiles,
            final int playerScore) {
        int heartX = 23;
        for (int i = 0; i < numeberOfLives; i++) {
            batch.draw(heart, heartX, 19, 1, 1);
            heartX += 1;
        }
        float projectilesX = 26;
        for (int i = 0; i < numerOfProjectiles; i++) {
            batch.draw(projectile, projectilesX, 19, 1, 1);
            projectilesX += 0.5f;
        }
    }

}
