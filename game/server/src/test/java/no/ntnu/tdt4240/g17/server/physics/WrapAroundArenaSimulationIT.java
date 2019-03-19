package no.ntnu.tdt4240.g17.server.physics;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;
import no.ntnu.tdt4240.g17.server.physics.util.ArenaTestUtil;
import no.ntnu.tdt4240.g17.server.physics.util.DesktopPhysicsVisualizer;

/**
 * Integration test to set up a basic physics world and test wrap-around of position
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class WrapAroundArenaSimulationIT {

    @BeforeAll
    static void setUpBox2d() {
        Box2D.init();
    }

    @Test @Disabled
    void simulateArenaWithHorizontalMovementAndWrappingAndGui() {
        // Numbers are players, - are arrows
        final String arena =
                "   -   \n" +
                "       \n" +
                "#######\n" +
                "       \n" +
                "   2   \n" +
                "#######\n" +
                "       \n" +
                "   1   \n" +
                "#######";
        final Rectangle arenaBounds = new Rectangle(0, 0, arena.split("\n")[0].length(), arena.length());

        // Configure
        final World world = new World(new Vector2(0, -9.81f), true);
        final MovePlayerLeftSystem movePlayerLeftSystem = new MovePlayerLeftSystem(WrapAroundSystem.FAMILY, 0);
        final WrapAroundSystem wrapAroundSystem = new WrapAroundSystem(1, arenaBounds);
        final PhysicsSystem physicsSystem = new PhysicsSystem(PhysicsSystem.PHYSICS_FAMILY, 2, 0.015f, world);

        final Engine engine = new Engine();
        engine.addSystem(physicsSystem);
        engine.addSystem(wrapAroundSystem);
        engine.addSystem(movePlayerLeftSystem);
        engine.addSystem(new ArenaSimulationIT.PlayerPositionDebugSystem(1));

        final List<Entity> entities = ArenaTestUtil.createComponentsFromArena(arena, world);
        for (final Entity entity : entities) {
            engine.addEntity(entity);
        }


        // Execute
        boolean useGui = true;
        log.info("Running simulation");
        runSimulation(world, engine, useGui);

        // Cleanup
        engine.removeAllEntities();
        world.dispose();
    }

    @Test @Disabled
    void simulateArenaWithFallingAndWrappingAndGui() {
        // Numbers are players, - are arrows
        final String arena =
                "# ### #\n" +
                " -     \n" +
                "     1 \n" +
                "# ### #\n" +
                "       \n" +
                "       \n" +
                "# ### #";
        final int width = arena.split("\n")[0].length();
        final int height = arena.split("\n").length;
        final Rectangle arenaBounds = new Rectangle(0, 0, width, height);

        // Configure
        final World world = new World(new Vector2(0, -9.81f), true);
        final WrapAroundSystem wrapAroundSystem = new WrapAroundSystem(1, arenaBounds);
        final PhysicsSystem physicsSystem = new PhysicsSystem(PhysicsSystem.PHYSICS_FAMILY, 2, 0.015f, world);

        final Engine engine = new Engine();
        engine.addSystem(physicsSystem);
        engine.addSystem(wrapAroundSystem);
        engine.addSystem(new ArenaSimulationIT.PlayerPositionDebugSystem(1));

        final List<Entity> entities = ArenaTestUtil.createComponentsFromArena(arena, world);
        for (final Entity entity : entities) {
            engine.addEntity(entity);
        }


        // Execute
        boolean useGui = true;
        log.info("Running simulation");
        runSimulation(world, engine, useGui);

        // Cleanup
        engine.removeAllEntities();
        world.dispose();
    }

    private static void runSimulation(final World world, final Engine engine, final boolean useGui) {
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
    }

    protected static final class MovePlayerLeftSystem extends IteratingSystem {
        public MovePlayerLeftSystem(final Family family, final int priority) {
            super(family, priority);
        }

        private float defaultSpeed = 7f;
        private Map<Entity, Float> speedMap = new IdentityHashMap<>();

        @Override
        protected void processEntity(final Entity entity, final float deltaTime) {
            final Box2dBodyComponent box2dBodyComponent = Box2dBodyComponent.MAPPER.get(entity);

            float speed = 0f;
            if (speedMap.containsKey(entity)) {
                speed = speedMap.get(entity);
            } else {
                speed = defaultSpeed;
                defaultSpeed = -defaultSpeed;
                speedMap.put(entity, speed);
            }

            final Body body = box2dBodyComponent.getBody();
            body.setLinearVelocity(speed, 0f);
        }
    }
}
