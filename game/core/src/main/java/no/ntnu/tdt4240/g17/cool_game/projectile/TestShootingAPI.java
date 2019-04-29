package no.ntnu.tdt4240.g17.cool_game.projectile;

import java.util.Random;

/**
 * Only used for offline testing of character movement.
 */
public class TestShootingAPI {
    private float xValue;
    private float yValue;
    private Random rand;
    private int xRange;
    private int yRange;
    private int yAcs;
    private float stepSize;
    /**
     * Constructor.
     */
    public TestShootingAPI() {
        this.rand = new Random();
        this.xValue = 0;
        this.yValue = 0;
        this.xRange = rand.nextInt(5);
        this.yRange = 0;
        this.yAcs = 2;
        this.stepSize = 0;

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
    public void changeValue() {
        if (this.xValue < 32f) {
            this.xValue += 0.03;
            this.stepSize += 0.02;
            this.yValue = new Float(Math.cos(this.stepSize + 32)) * 6 + 10;
        }

        //if (this.xValue > 1000) {
         //   this.xValue = 0;
        //}
    }

    /**
     * next movement.
     */
    private void rollTheDice() {
        int dice = this.rand.nextInt(6);
        if (dice == 0 || dice == 1) {
            this.xRange = -(this.rand.nextInt(100) + 3);
        } else if (dice == 2 || dice == 3) {
            this.xRange = this.rand.nextInt(100) + 20;
        } else if (dice == 4) {
            this.yRange = -(this.rand.nextInt(10) + 10);
        } else {
            this.yRange = this.rand.nextInt(10) + 10;
        }
    }
}
