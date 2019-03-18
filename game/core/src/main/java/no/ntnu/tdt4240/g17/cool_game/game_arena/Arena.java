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
 * Created by Johannes Tomren Røsvik (@rosvik) on 3/11/2019.
 *
 * @author Johannes Tomren Røsvik (@rosvik)
 */

@Slf4j
public class Arena {

    /**
     * Documentation: https://github.com/libgdx/libgdx/wiki/Tile-maps .
     */
    private TiledMap map;

    /**
     * Path to .tmx file.
     */
    private String filePath;

    /**
     * Renderer.
     */
    private OrthogonalTiledMapRenderer renderer;

    /**
     * Number of tiles in height.
     */
    private float height;

    /**
     * Number of tiles in width.
     */
    private float width;

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

        map = new TmxMapLoader().load(filePath);

        renderer = new OrthogonalTiledMapRenderer(map, 1 / tileSize, batch);
        OrthographicCamera camera = new OrthographicCamera();

        camera.setToOrtho(false, width, height);
        renderer.setView(camera);
    }

    /**
     * Render the arena.
     */
    public void renderArena() {

        MapLayer mapLayer = getLayer("map");
        MapLayer backgroundLayer = getLayer("background");

        drawLayer(mapLayer);
        drawLayer(backgroundLayer);
    }

    /**
     * Render the foreground layers.
     */
    public void renderForeground() {
        MapLayer foregroundLayer = getLayer("foreground");
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
     * @param layer Name of layer.
     * @throws RuntimeException When a required layer is not found.
     * @return MapLayer The layer with given layer name.
     */
    public MapLayer getLayer(final String layer) {
        int mapIndex = map.getLayers().getIndex(layer);

        if (mapIndex < 0 && layer.equals("map")) {
            throw new RuntimeException("Could not find layer " + layer + " in file " + filePath);
        } else if (mapIndex < 0) {
            log.warn("Could not find layer {} in file {}", layer, filePath);
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
     * Get a string representation of the arena layout.
     *
     * @return A string representation of the arena layout.
     */
    public String toString() {
        // multi[y][x]
        boolean[][] multi = getTiles();

        StringBuilder out = new StringBuilder();

        // y values are counted from bottom to top
        for (int y = multi.length - 1; y >= 0; y--) {

            // x values are counted from left to right
            for (int x = 0; x < multi[y].length; x++) {

                if (multi[y][x]) {
                    out.append('X');
                } else {
                    out.append('-');
                }

            }
            out.append('\n');
        }
        return out.toString();
    }

    /**
     * Get the height in blocks of the Arena.
     *
     * @return The height in blocks of the Arena.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Get the width in blocks of the Arena.
     *
     * @return The width in blocks of the Arena.
     */

    public float getWidth() {
        return width;
    }
}
