package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PlayerStartPosition {
    public float positionX;
    public float positionY;
    private String filePath;
    private TiledMap map;
    private ArrayList<Vector2> positions;

    public PlayerStartPosition(final String filePath) {
        this.filePath = filePath;
        map = new TmxMapLoader().load(filePath);
    }

    public ArrayList<Vector2> getStartPositions() {
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) map.getLayers().get("Players");

        Integer width = map.getProperties().get("width", Integer.class);
        Integer height = map.getProperties().get("height", Integer.class);

        positions = new ArrayList<>();

        // y values are counted from bottom to top
        for (int y = width - 1; y >= 0; y--) {

            // x values are counted from left to right
            for (int x = 0; x < height; x++) {

                if (mapLayer.getCell(x, y) != null) {
                    positions.add(new Vector2(x, y));
                }

            }
        }

        if (positions.size() != 4) {
            throw new RuntimeException("Found " + positions.size() + " player positions while loading map '" + filePath + "'. Expected 4.");
        }

        return positions;
    }
}
