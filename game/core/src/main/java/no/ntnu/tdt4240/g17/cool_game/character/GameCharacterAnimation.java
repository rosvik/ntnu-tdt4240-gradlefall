package no.ntnu.tdt4240.g17.cool_game.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Character animation.
 */
public class GameCharacterAnimation {

    private Animation<TextureRegion> animation;
    private final Animation<TextureRegion> RUNNING_ANIMATION;
    private final Animation<TextureRegion> IDLE_ANIMATION;
    private final Animation<TextureRegion> JUMPING_ANIMATION;
    private final Animation<TextureRegion> FALLING_ANIMATION;
    private final float FRAME_DURATION = 1 / 8f;
    private boolean isMovingLeft;

    /**
     * Consturctor.
     * @param name = character name, must be the same as the character animation in DungeonTilset.atals. e.g. wizzard_f, knigth_m, zombie...
     * @param atlas = Textureatlas whit animation sprites
     */
    public GameCharacterAnimation(final String name, final TextureAtlas atlas) {
        this.isMovingLeft = false;
        this.IDLE_ANIMATION = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegions(name + "_idle_anim"));
        this.RUNNING_ANIMATION = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegions(name + "_run_anim"));
        this.JUMPING_ANIMATION = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegion(name + "_idle_anim"));
        if (atlas.findRegion(name + "_hit_anim") == null) {
            this.FALLING_ANIMATION = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegions(name + "_idle_anim").get(2));
        } else {
            this.FALLING_ANIMATION = new Animation<TextureRegion>(FRAME_DURATION, atlas.findRegion(name + "_hit_anim"));
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
     * Jump.
     */
    public void jump() {
        this.animation = this.JUMPING_ANIMATION;
    }

    /**
     * Fall.
     */
    public void fall() {
        this.animation = this.FALLING_ANIMATION;
    }

    /**
     * Move character left
     * Temporary, will be change to support vectors from controller/server.
     */
    public void moveLeft() {
        this.isMovingLeft = true;
        this.animation = this.RUNNING_ANIMATION;
    }

    /**
     * Move character right
     * Temporary, will be change to support vectors from controller/server.
     */
    public void moveRigth() {
        this.isMovingLeft = false;
        this.animation = this.RUNNING_ANIMATION;
    }

    /**
     * idle.
     */
    public void idle() {
        this.animation = this.IDLE_ANIMATION;
    }

    /**
     * hit.
     */
    public void hit() {
        this.animation = this.FALLING_ANIMATION;
    }

    /**
     * @return the height of the sprite
     */
    public float getHeight() {
        return this.animation.getKeyFrame(0).getRegionHeight();
    }

    /**
     * @return the width of the sprite
     */
    public float getWidth() {
        if (this.isMovingLeft) {
            return -this.animation.getKeyFrame(0).getRegionHeight();
        }
        return this.animation.getKeyFrame(0).getRegionHeight();
    }

    /**
     * @param stateTime
     * @return
     */
    public TextureRegion getFrame(final float stateTime) {
        return this.animation.getKeyFrame(stateTime, true);
    }
}
