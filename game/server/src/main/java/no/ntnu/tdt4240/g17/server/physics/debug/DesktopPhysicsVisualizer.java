package no.ntnu.tdt4240.g17.server.physics.debug;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * For debugging. Opens a GUI with the world.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class DesktopPhysicsVisualizer implements ApplicationListener {

    private static final int PX_PER_METER = 20;
    private Box2DDebugRenderer box2DDebugRenderer;

    private World world = null;
    private OrthographicCamera camera = new OrthographicCamera();
    @Nullable
    private Consumer<Float> updateCallback;
    @Setter
    private boolean shouldStop = false;
    private boolean hasCalledExit = false;

    /**
     * @param box2dWorld world to render
     * @param onUpdateCallback called when gui is updated
     * @return the main thread for the gui
     */
    public Thread startGui(final World box2dWorld, @Nullable final Consumer<Float> onUpdateCallback) {
        this.updateCallback = onUpdateCallback;
        this.world = box2dWorld;
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.forceExit = false;
        final LwjglApplication lwjglApplication = new LwjglApplication(this, config);
        try {
            final Field mainLoopThread = LwjglApplication.class.getDeclaredField("mainLoopThread");
            mainLoopThread.setAccessible(true);
            return (Thread) mainLoopThread.get(lwjglApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create() {
        log.debug("Visualizer created");
        box2DDebugRenderer = new Box2DDebugRenderer(true, false, false, true, true, true);
        camera.setToOrtho(false, Gdx.graphics.getWidth() / PX_PER_METER,
                Gdx.graphics.getHeight() / PX_PER_METER);
        camera.position.sub(4, 2, 0); // Shift the camera to see the outside
    }

    @Override
    public void resize(final int width, final int height) {
        Gdx.gl.glViewport(0, 0, width, height);
        camera.setToOrtho(false, width / PX_PER_METER, height / PX_PER_METER);
        camera.position.sub(4, 2, 0);
    }

    @Override
    public void render() {
        if (shouldStop) {
            if (!hasCalledExit) {
                hasCalledExit = true;
                try {
                    dispose();
                } catch (Exception ignored) { }
                Gdx.app.exit();
            }
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (updateCallback != null) {
            updateCallback.accept(Gdx.graphics.getDeltaTime());
        }

        if (world != null) {
            camera.update();
            box2DDebugRenderer.render(world, camera.combined);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
    }
}
