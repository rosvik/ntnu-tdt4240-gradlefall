package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Characters animation.
 */
public class GameCharacterAnimation {

    private Animation<TextureRegion> animation;
    private final Animation<TextureRegion> runningAnimation;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> jumpingAnimation;
    private final Animation<TextureRegion> fallingAnimation;
    private static final float FRAME_DURATION = 1 / 8f;
    private boolean isMovingLeft;
    private static final int TILE_SCALE_CONSTANT = 16;

    /**
     * Consturctor.
     * @param name = character name, must be the same as the character animation in DungeonTilset.atlas.
     * @param atlas = Textureatlas whit animation sprites
     */
    public GameCharacterAnimation(final String name, final TextureAtlas atlas) {
        this.isMovingLeft = false;
        this.idleAnimation = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegions(name + "_idle_anim"));
        this.runningAnimation = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegions(name + "_run_anim"));
        this.jumpingAnimation = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegion(name + "_idle_anim"));

        if (atlas.findRegion(name + "_hit_anim") == null) {
            this.fallingAnimation =
                    new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegions(name + "_idle_anim").get(2));
        } else {
            this.fallingAnimation = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegion(name + "_hit_anim"));
        }
        this.idle();

    }

    /**
     * @return current animation.
     */
    public Animation<TextureRegion> getAnimation() {
        return this.animation;
    }

    /**
     * Changes the animation to jumping animation.
     */
    public void jump() {
        this.animation = this.jumpingAnimation;
    }

    /**
     * Changes the animation to falling animation.
     */
    public void fall() {
        this.animation = this.fallingAnimation;
    }

    /**
     * Changes the animation to running animation and sets movingLeft to true.
     */
    public void moveLeft() {
        this.isMovingLeft = true;
        this.animation = this.runningAnimation;
    }

    /**
     * Move character right
     * Temporary, will be change to support vectors from controller/server.
     */
    public void moveRigth() {
        this.isMovingLeft = false;
        this.animation = this.runningAnimation;
    }

    /**
     * Changes the animation to idle animation.
     */
    public void idle() {
        this.animation = this.idleAnimation;
    }

    /**
     * Changes the animation to fall (hit) animation.
     */
    public void hit() {
        this.animation = this.fallingAnimation;
    }

    /**
     * @return the height of the sprite
     */
    public float getHeight() {
        return this.animation.getKeyFrame(0).getRegionHeight() / TILE_SCALE_CONSTANT;
    }

    /**
     * @return the width of the sprite
     */
    public float getWidth() {
        if (this.isMovingLeft) {
            return -this.animation.getKeyFrame(0).getRegionHeight() / TILE_SCALE_CONSTANT;
        }
        return this.animation.getKeyFrame(0).getRegionHeight() / TILE_SCALE_CONSTANT;
    }

    /**
     * @return running animation.
     */
    public Animation<TextureRegion> getRunningAnimation() {
        return runningAnimation;
    }

    /**
     * @return idle animation.
     */
    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    /**
     * @return jumping animation.
     */
    public Animation<TextureRegion> getJumpingAnimation() {
        return jumpingAnimation;
    }

    /**
     * @return falling animation.
     */
    public Animation<TextureRegion> getFallingAnimation() {
        return fallingAnimation;
    }

    /**
     * @return true if animation is movingleft
     */
    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    /**
     * @param stateTime = current time of animation.
     * @return current frame in animation
     */
    public TextureRegion getFrame(final float stateTime) {
        return this.animation.getKeyFrame(stateTime, true);
    }
}
