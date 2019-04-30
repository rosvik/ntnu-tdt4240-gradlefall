package no.ntnu.tdt4240.g17.cool_game.screens.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectileComponentTest extends GameTest {

    ProjectileComponent projectile;
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./TextureAtlas/Projectiles/Projectiles.atlas"));
    @BeforeEach
    void setUp() {
        Position position = new Position();
        position.x = 0;
        position.y = 0;
        projectile = new ProjectileComponent("0", position, "arrow", 135, atlas);
    }

    @Test
    void render() {
        projectile.render(0,0,0);
        projectile.render(1f,1f,1f);
        Assert.assertThat("x should be",1f, Matchers.equalTo(projectile.getProjectile().getState().getxPosition()));
        Assert.assertThat("y should be", 1f, Matchers.equalTo(projectile.getProjectile().getState().getyPosition()));
        Assert.assertThat("Angle should be",1f, Matchers.equalTo(projectile.getProjectile().getState().getDirectionAngle()));

    }
}