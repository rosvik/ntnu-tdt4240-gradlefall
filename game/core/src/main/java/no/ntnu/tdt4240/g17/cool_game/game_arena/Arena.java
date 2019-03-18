package no.ntnu.tdt4240.g17.cool_game.game_arena;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import lombok.extern.slf4j.Slf4j;

/**
 * Game arena.
 */
@Slf4j
public class Arena {

    /**
     * Documentation: https://github.com/libgdx/libgdx/wiki/Tile-maps .
     */
    private TiledMap map;

    /**
     * Batch.
     */
    private Batch batch;

    /**
     * Path to .tmx file.
     */
    private String filePath;

    /**
     * Path to file.
     */
    private Texture background;

    /**
     * Renderer.
     */
    private OrthogonalTiledMapRenderer renderer;

    /**
     * Scale.
     */
    private float unitScale;
    /**
     * Pixel size (width and height) of a tile.
     */
    private float tileSize;
    /**
     * Number of tiles in height.
     */
    private float height;
    /**
     * Number of tiles in width.
     */
    private float width;

    /**
     * Walls for collision detection.
     */
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };


    /**
     * @param filePath Path to .tmx file.
     * @param tileSize Pixel size (width and height) of a tile.
     * @param height   Number of tiles in height.
     * @param width    Number of tiles in width.
     * @param batch    The batch to render the arena to.
     */
    public Arena(
            final String filePath,
            final float tileSize,
            final float width,
            final float height,
            final Batch batch
    ) {

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
     */
    public void render() {

        MapLayer mapLayer = getLayer("map");
        MapLayer backgroundLayer = getLayer("background");
        MapLayer foregroundLayer = getLayer("foreground");

        if (background != null) {
            batch.draw(background, 0f, 0f, width, height);
        }

        drawLayer(mapLayer);
        drawLayer(backgroundLayer);

        // TODO: Characters should be rendered between these layers.

        drawLayer(foregroundLayer);
    }


    /**
     * Render a specific layer of the arena.
     *
     * @param layer The MapLayer that should be rendered.
     */
    public void drawLayer(final MapLayer layer) {

        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) layer;

        renderer.renderTileLayer(tiledMapTileLayer);

    }

    /**
     * Set the background texture.
     *
     * @param image Path to the image file.
     */
    public void setBackground(final String image) {
        background = new Texture(image);
    }

    /**
     * @param layer Name of layer.
     * @throws RuntimeException When a required layer is not found.
     * @return MapLayer The layer with given layer name.
     */
    public MapLayer getLayer(final String layer) {
        int mapIndex = map.getLayers().getIndex(layer);

        if (mapIndex < 0 && layer.equals("map")) {
            throw new RuntimeException("Could not find layer " + layer + " in file " + filePath);
        } else if (mapIndex < 0) {
            log.warn("Could not find layer %s in file %s", layer, filePath);
            return new MapLayer();
        }

        int index = map.getLayers().getIndex(layer);
        return map.getLayers().get(index);
    }

    /**
     * Get a 2D array describing the layout of the arena.
     *
     * @return boolean[][] A 2D array where true signifies that a block is present at [y][x]
     */
    public boolean[][] getTiles() {
        // tiles[y][x]
        boolean[][] tiles = new boolean[(int) height][(int) width];

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) getLayer("map");

        // y values are counted from bottom to top
        for (int y = tiles.length - 1; y >= 0; y--) {

            // x values are counted from left to right
            for (int x = 0; x < tiles[y].length; x++) {

                // Set the value to true if there is a block
                tiles[y][x] = mapLayer.getCell(x, y) != null;

            }
        }

        return tiles;
    }

    /**
     * Print the layout of the arena to the console.
     */
    public void printTiles() {
        // multi[y][x]
        boolean[][] multi = getTiles();

        // y values are counted from bottom to top
        for (int y = multi.length - 1; y >= 0; y--) {

            // x values are counted from left to right
            for (int x = 0; x < multi[y].length; x++) {

                if (multi[y][x]) {
                    System.out.print('X');
                } else {
                    System.out.print('-');
                }

            }
            System.out.println();
        }
    }

}
