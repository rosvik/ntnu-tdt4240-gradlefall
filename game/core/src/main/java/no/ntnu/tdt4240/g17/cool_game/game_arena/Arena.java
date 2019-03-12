package no.ntnu.tdt4240.g17.cool_game.game_arena;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import lombok.extern.slf4j.Slf4j;

/**
 * Game arena.
 */
@SuppressWarnings("CheckStyle")
@Slf4j
public class Arena {

    /**
     * Path to file.
     */
    private String filePath;

    /**
     * Documentation: https://github.com/libgdx/libgdx/wiki/Tile-maps .
     */
    private TiledMap map;

    /** Batch */
    private Batch batch;

    /** Scale. */
    private float unitScale;
    /** Pixel size (width and height) of a tile.. */
    private float tileSize;
    /** Number of tiles in height.. */
    private float height;
    /** Number of tiles in width.. */
    private float width;

    /** Renderer. */
    private OrthogonalTiledMapRenderer renderer;


    /**
     * @param filePath Path to .tmx file.
     * @param tileSize Pixel size (width and height) of a tile.
     * @param height Number of tiles in height.
     * @param width Number of tiles in width.
     */
    public Arena(final String filePath, final float tileSize, final float width, final float height, final Batch batch) {

        this.filePath = filePath;
        this.height = height;
        this.width = width;
        this.tileSize = tileSize;
        this.batch = batch;

        map = new TmxMapLoader().load(filePath);

        unitScale = 1 / tileSize;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale, batch);
        OrthographicCamera camera = new OrthographicCamera();

        camera.setToOrtho(false, width, height);
        renderer.setView(camera);
    }

    /**
     * Render the arena.
     * @throws LayerNotFoundException When a required layer was not found.
     */
    public void render() throws LayerNotFoundException {

        MapLayer mapLayer = getLayer("map");
        MapLayer backgroundLayer = getLayer("background");
        MapLayer foregroundLayer = getLayer("foreground");

        drawLayer(mapLayer);
        drawLayer(backgroundLayer);

        // TODO: Characters should be rendered between these layers.

        drawLayer(foregroundLayer);

    }


    /**
     * Render a specific layer of the map.
     *
     * @param layer The MapLayer that should be rendered.
     */
    public void drawLayer(final MapLayer layer) {

        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) layer;

        renderer.renderTileLayer(tiledMapTileLayer);

    }

    /**
     * @param layer Name of layer.
     * @throws LayerNotFoundException When a required layer is not found.
     */
    public MapLayer getLayer(final String layer) {
        int mapIndex = map.getLayers().getIndex(layer);

        if (mapIndex < 0 && layer.equals("map")) {
            throw new LayerNotFoundException(layer, filePath);
        } else if (mapIndex < 0) {
            log.warn("Could not find layer %s in file %s", layer, filePath);
            return new MapLayer();
        }

        int index = map.getLayers().getIndex(layer);
        return map.getLayers().get(index);
    }
}

/**
 * When a required layer is not found.
*/
class LayerNotFoundException extends RuntimeException {
    /**
     * @param layerName Name of the layer that was not found.
     * @param fileName Name of the current file
     */
    LayerNotFoundException(final String layerName, final String fileName) {
        super("Could not find layer " + layerName + " in file " + fileName);
    }
}
