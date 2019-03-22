package no.ntnu.tdt4240.g17.cool_game.projectile;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;

class ProjectileTest extends GameTest {
    TextureAtlas atlas = new TextureAtlas("TextureAtlas/Projectiles/Projectiles.atlas");
    Projectile testProjectile = new Projectile("arrow", 0,0, 135, atlas);

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
        testProjectile.render(0,0);
        Assert.assertTrue(x == testProjectile.getState().getxPosition());
        Assert.assertTrue(y == testProjectile.getState().getyPosition());
        Assert.assertTrue(angle == testProjectile.getState().getAngle());

        testProjectile.render(10,10);
        Assert.assertFalse(x == testProjectile.getState().getxPosition());
        Assert.assertFalse(y == testProjectile.getState().getyPosition());
        Assert.assertFalse(angle == testProjectile.getState().getAngle());
    }

    @Test
    void draw() {
    }
}