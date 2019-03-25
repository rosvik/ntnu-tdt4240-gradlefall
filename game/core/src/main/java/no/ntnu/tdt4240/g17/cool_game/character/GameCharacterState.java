package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.ntnu.tdt4240.g17.cool_game.projectile.Projectile;

import java.util.Stack;

/**
 * State of a gamecharacter.
 * - posistion
 * - number of lives
 * - character score
 * - number of projectiles
 */
public class GameCharacterState {
    private float xPosition;
    private float yPosition;
    private int lives;
    private int score;
    private Projectile projectileType;
    private Stack<Projectile> projectiles = new Stack<>();

    /**
     * Constructor.
     * @param xPosisiton = the inital x posistion
     * @param yPosisiton = the inital y posistion'
     * @param projectileName = the name of projectilesprite in TextureAtlas
     * @param projectiles = the projectiles TextureAtlas
     */
    public GameCharacterState(final float xPosisiton,
                              final float yPosisiton,
                              final String projectileName,
                              final TextureAtlas projectiles
    ) {
        this.xPosition = xPosisiton;
        this.yPosition = yPosisiton;
        this.lives = 3;
        this.score = 0;
        this.projectileType = new Projectile(projectileName, 0, 0, 135, projectiles);
        this.addProjectiles(3);
    }

    /**
     * @return the y-posistion to the character.
     */
    public float getyPosition() {
        return yPosition;
    }

    /**
     * @param yPosition = the new y posistion
     */
    public void setyPosition(final float yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * @return the x-posistion to the character
     */
    public float getxPosition() {
        return xPosition;
    }

    /**
     * @param xPosisiton = the new x-posistion
     */
    public void setxPosition(final float xPosisiton) {
        this.xPosition = xPosisiton;
    }

    /**
     * @param newxPosition = the new x-position
     * @param newyPosition = the new y-position
     */
    public void setPosition(final float newxPosition, final float newyPosition) {
        this.xPosition = newxPosition;
        this.yPosition = newyPosition;
    }

    /**
     * @return number of lives left
     */
    public int getLives() {
        return lives;
    }

    /**
     * @param increment = number of new lives
     */
    public void addLives(final int increment) {
        this.lives += increment;
    }

    /**
     * Checks if character has no more lives.
     * @return true if character is dead.
     */
    public boolean isDead() {
        return this.lives == 0;
    }

    /**
     * @param decrement = number of lives to remove
     */
    public void removeLives(final int decrement) {
        this.lives -= decrement;
    }

    /**
     * @return the character score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param increment = increases the character score
     */
    public void increaseScore(final int increment) {
        this.score += increment;
    }

    /**
     * Shoot.
     * TODO Projectile.shoot or something
     */
    public void shoot() {
        if (!this.projectiles.empty()) {
            this.projectiles.pop();
        }
    }

    /**
     * @param numberOfProjectiles = how many projectiles to add
     */
    public void addProjectiles(final int numberOfProjectiles) {
        for (int i = 1; i <= numberOfProjectiles; i++) {
            this.projectiles.add(this.projectileType);
        }
    }

    /**
     * @return number of projectiles
     */
    public int getNumberOfProjectiles() {
        return this.projectiles.size();
    }
}
