package no.ntnu.tdt4240.g17.cool_game.projectile;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;

class ProjectileTest extends GameTest {
    TextureAtlas atlas = new TextureAtlas("TextureAtlas/Projectiles/Projectiles.atlas");
    Projectile testProjectile = new Projectile("arrow", 0,0, 135, 0, atlas);

    @Test
    void getState() {
        Assert.assertThat(testProjectile.getState(), Matchers.isA(ProjectileState.class));
    }

    @Test
    void getScale() {
        Assert.assertTrue(1 == testProjectile.getScale(24f));
    }

    @Test
    void render() {
        float x = testProjectile.getState().getxPosition();
        float y = testProjectile.getState().getyPosition();
        float angle = testProjectile.getState().getAngle();
        System.out.println(testProjectile.getState().getyPosition());
        testProjectile.render(0,0f);
        System.out.println(testProjectile.getState().getyPosition());
        Assert.assertThat(y, Matchers.equalTo(testProjectile.getState().getyPosition()));
        Assert.assertThat(x, Matchers.equalTo(testProjectile.getState().getxPosition()));
        Assert.assertThat(angle, Matchers.equalTo(testProjectile.getState().getAngle()));
        Assert.assertTrue(angle == testProjectile.getState().getAngle());

        testProjectile.render(10,10);
        Assert.assertThat(10f, Matchers.equalTo(testProjectile.getState().getxPosition()));
        Assert.assertThat(10f, Matchers.equalTo(testProjectile.getState().getyPosition()));
        Assert.assertFalse(angle == testProjectile.getState().getAngle());
    }

    @Test
    void draw() {
    }
}