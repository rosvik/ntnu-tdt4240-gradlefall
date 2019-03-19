package no.ntnu.tdt4240.g17.server.physics;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;
import no.ntnu.tdt4240.g17.server.physics.util.ArenaTestUtil;
import no.ntnu.tdt4240.g17.server.physics.util.DesktopPhysicsVisualizer;

/**
 * Integration test to set up a basic physics world and let it tick for some iterations.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Tag("integration")
@Slf4j
public class ArenaSimulationIT {

    @BeforeAll
    static void setupBox2d() {
        Box2D.init();
    }

    /**
     * Run this to get a GUI and visualize the box2d world, or simply run it to log numerical values.
     *
     * Change <code>useGui</code> to <code>false</code> to only log values,
     * and <code>true</code> (default) to show the gui.
     */
    @Test @Disabled
    void simulateArena() {
        // Configure
        final World world = new World(new Vector2(0, -9.81f), true);
        final PhysicsSystem physicsSystem = new PhysicsSystem(PhysicsSystem.PHYSICS_FAMILY, 0, 0.015f, world);
        final Engine engine = new Engine();
        engine.addSystem(physicsSystem);
        engine.addSystem(new PlayerPositionDebugSystem(1));

        // Numbers are players, - are arrows
        final String arena =
                        "### ###\n" +
                        "#     #\n" +
                        "#-   3#\n" +
                        "#     #\n" +
                        "#     #\n" +
                        "# ### #\n" +
                        "#     #\n" +
                        "#2 1  #\n" +
                        "### ###";

        final List<Entity> entities = ArenaTestUtil.createComponentsFromArena(arena, world);
        for (final Entity entity : entities) {
            engine.addEntity(entity);
        }


        // Execute
        boolean useGui = true;
        log.info("Running simulation");
        if (useGui) {
            final DesktopPhysicsVisualizer gui = new DesktopPhysicsVisualizer();
            log.info("Gui started");
            try {
                gui.startGui(world, (Float delta) -> {
                    engine.update(delta);
                }).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            boolean running = true;
            int iteration = 0;
            while (running) {
                engine.update(0.2f);

                if (iteration > 5000) {
                    running = false;
                }
                iteration++;
            }
        }

        // Cleanup
        engine.removeAllEntities();
        world.dispose();
    }

    /**
     * Prints player positions every {@value #FREQUENCY} iterations.
     */
    @Slf4j
    protected static class PlayerPositionDebugSystem extends IteratingSystem {

        public static final Family FAMILY = Family.all(PlayerComponent.class, TransformComponent.class).get();
        public static final int FREQUENCY = 300;

        int iteration = 0;

        public PlayerPositionDebugSystem(final int priority) {
            super(FAMILY, priority);
        }

        @Override
        public void update(final float deltaTime) {
            super.update(deltaTime);
            iteration++;
        }

        @Override
        protected void processEntity(final Entity entity, final float deltaTime) {
            if (iteration % FREQUENCY == 0) {
                final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);
                final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);

                log.trace("Player {} is at {}", playerComponent.getId(), transformComponent.getPosition());
            }
        }
    }
}
