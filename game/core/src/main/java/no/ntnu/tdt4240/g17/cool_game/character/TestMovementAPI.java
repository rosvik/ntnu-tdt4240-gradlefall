package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * Only used for offline testing of character movement.
 */
public class TestMovementAPI {
    private int xValue;
    private int yValue;
    private Random rand;
    private int xRange;
    private int yRange;
    /**
     * Constructor.
     */
    public TestMovementAPI() {
        this.rand = new Random();
        this.xValue = this.rand.nextInt(50) + 200;
        this.yValue = this.rand.nextInt(50) + 200;
        this.xRange = rand.nextInt(20);
        this.yRange = 0;

    }

    /**
     * @return x-value.
     */
    public int getxValue() {
        return this.xValue;
    }

    /**
     * @return y-value.
     */
    public int getyValue() {
        return this.yValue;
    }

    /**
     * changes pos-value.
     */
    public void changeXvalue() {
        if (this.xRange == 0 && this.yRange == 0) {
            this.rollTheDice();
        } else if (this.xRange > 0) {
            if (this.xValue + 50 < Gdx.graphics.getWidth()) {
                this.xValue += 1;
            }
            this.xRange -= 1;
        } else if (this.xRange < 0) {
            if (this.xValue + 50 > 0) {
                this.xValue -= 1;
            }
            this.xRange += 1;
        } else if (this.yRange > 0) {
            if (this.yValue > 0) {
                this.yValue -= 1;
            }
            this.yRange -= 1;
        } else if (this.yRange < 0) {
            if (this.yValue + 50 < Gdx.graphics.getHeight()) {
                this.yValue += 1;
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
            this.xRange = -(this.rand.nextInt(100) + 20);
        } else if (dice == 2 || dice == 3) {
            this.xRange = this.rand.nextInt(100) + 20;
        } else if (dice == 4) {
            this.yRange = -(this.rand.nextInt(10) + 10);
        } else {
            this.yRange = this.rand.nextInt(10) + 10;
        }
    }
}
