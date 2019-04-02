package no.ntnu.tdt4240.g17.cool_game.screens.game.projectile;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectileSystemTest extends GameTest {

    ProjectileSystem system;
    Engine engine;
    Entity projectile;
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./TextureAtlas/Projectiles/Projectiles.atlas"));
    Position position;

    @BeforeEach
    void setUp() {
        system = new ProjectileSystem();
        engine = new Engine();
        projectile = new Entity();
        position = new Position();
        position.x = 0;
        position.y = 0;
        projectile.add( new ProjectileComponent("0", position, "arrow", 135, atlas));
        engine.addSystem(system);
    }

    @Test
    void shouldGetters() {
        system.getFamily();
        system.getClientData();
        system.getProjectile();
    }

    @Test
    void shouldUpdate() {
        system.update(1f);
    }

    @Test
    void addedToEngine() {
        Assert.assertTrue(system.getEntitiesToUpdate() != null);
    }

    @Test
    void removedFromEngine() {
        system.removedFromEngine(engine);
    }
}