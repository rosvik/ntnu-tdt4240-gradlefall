package no.ntnu.tdt4240.g17.cool_game.character;


import java.util.Random;

/**
 * Only used for offline testing of character movement.
 */
public class TestMovementAPI {
    private float xValue;
    private float yValue;
    private Random rand;
    private float xRange;
    private float yRange;
    private float xspeed = 0.05f;
    private float yspeed = 0.1f;
    /**
     * Constructor.
     */
    public TestMovementAPI() {
        this.rand = new Random();
        this.xValue = this.rand.nextInt(10) + 5;
        this.yValue = this.rand.nextInt(10) + 5;
        this.xRange = rand.nextInt(10) + 5;
        this.yRange = 0;

    }

    /**
     * @return x-value.
     */
    public float getxValue() {
        return this.xValue;
    }

    /**
     * @return y-value.
     */
    public float getyValue() {
        return this.yValue;
    }

    /**
     * changes pos-value.
     */
    public void changeXvalue() {
        if (this.xRange == 0 && this.yRange == 0) {
            this.rollTheDice();
        } else if (this.xRange > 0) {
            if (this.xValue + 1 < 32f) {
                this.xValue += xspeed;
            }
            this.xRange -= 1;
        } else if (this.xRange < 0) {
            if (this.xValue + 1 > 0) {
                this.xValue -= xspeed;
            }
            this.xRange += 1;
        } else if (this.yRange > 0) {
            if (this.yValue > 0) {
                this.yValue -= yspeed;
            }
            this.yRange -= 1;
        } else if (this.yRange < 0) {
            if (this.yValue + 1 < 16f) {
                this.yValue += yspeed;
            }
            this.yRange += 1;
        }
    }

    /**
     * next movement.
     */
    private void rollTheDice() {
        int dice = this.rand.nextInt(6);
        if (dice == 0 || dice == 1) {
            this.xRange = -(this.rand.nextInt(16) + 10);
        } else if (dice == 2 || dice == 3) {
            this.xRange = this.rand.nextInt(16) + 10;
        } else if (dice == 4) {
            this.yRange = -(this.rand.nextInt(8) + 5);
        } else {
            this.yRange = this.rand.nextInt(8) + 5;
        }
    }
}
