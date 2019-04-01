package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileComponentTest extends GameTest {
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./TextureAtlas/Projectiles/Projectiles.atlas"));
    ProjectileComponent projectileComponent = new ProjectileComponent("arrow",atlas,135);
    @Test
    void shoot() {
        Assert.assertThat(projectileComponent.getNumberOfProectiles(), Matchers.equalTo(10));
        projectileComponent.shoot(0,0,0);
        Assert.assertThat(projectileComponent.getNumberOfProectiles(), Matchers.equalTo(9));
    }

    @Test
    void incrementProjectiles() {
        Assert.assertThat(projectileComponent.getNumberOfProectiles(), Matchers.equalTo(10));
        projectileComponent.incrementProjectiles();
        Assert.assertThat(projectileComponent.getNumberOfProectiles(), Matchers.equalTo(11));
    }

    @Test
    void renderProjectiles() {
    }
}