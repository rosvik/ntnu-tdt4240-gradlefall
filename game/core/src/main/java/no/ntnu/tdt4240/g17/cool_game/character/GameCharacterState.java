package no.ntnu.tdt4240.g17.cool_game.character;

/**
 * State of a gamecharacter.
 * - posistion
 * - number of lives
 * - character score
 */
public class GameCharacterState {
    private float xPosition;
    private float yPosition;
    private int lives;
    private int score;

    /**
     * Constructor.
     * @param xPosisiton = the inital x posistion
     * @param yPosisiton = the inital y posistion'
     */
    public GameCharacterState(final float xPosisiton, final float yPosisiton) {
        this.xPosition = xPosisiton;
        this.yPosition = yPosisiton;
        this.lives = 3;
        this.score = 0;
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
}
