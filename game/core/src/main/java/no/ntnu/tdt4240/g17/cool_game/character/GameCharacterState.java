package no.ntnu.tdt4240.g17.cool_game.character;

import no.ntnu.tdt4240.g17.cool_game.projectile.Projectile;

import java.util.Stack;

/**
 * State of a gamecharacter
 * - posistion
 * - number of lives
 * - character score
 */
public class GameCharacterState {
    private int xPosition;
    private int yPosition;
    private int lives;
    private int score;
    private Projectile projectileType;
    private Stack<Projectile> projectiles = new Stack<>();

    /**
     * Constructor.
     * @param xPosisiton = the inital x posistion
     * @param yPosisiton = the inital y posistion'
     */
    public GameCharacterState(final int xPosisiton, final int yPosisiton) {
        this.xPosition = xPosisiton;
        this.yPosition = yPosisiton;
        this.lives = 3;
        this.score = 0;
        this.projectileType = new Projectile();
        this.addProjectiles(3);
    }

    /**
     * @return the y-posistion to the character.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * @param yPosition = the new y posistion
     */
    public void setyPosition(final int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * @return the x-posistion to the character
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * @param xPosisiton = the new x-posistion
     */
    public void setxPosition(final int xPosisiton) {
        this.xPosition = xPosisiton;
    }

    /**
     * @param xPosition = the new x-position
     * @param yPosition = the new y-position
     */
    public void setPosition(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * @return number of lives left
     */
    public int getLives() {
        return lives;
    }

    /**
     * @param lives = number of new lives
     */
    public void addLives(final int lives) {
        this.lives += lives;
    }

    /**
     * Checks if character has no more lives.
     * @return true if character is dead.
     */
    public boolean isDead() {
        return this.lives == 0;
    }

    /**
     * @param lives = number of lives to remove
     */
    public void removeLives(final int lives) {
        this.lives -= lives;
    }

    /**
     * @return the character score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score = increases the character score
     */
    public void increaseScore(final int score) {
        this.score += score;
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
