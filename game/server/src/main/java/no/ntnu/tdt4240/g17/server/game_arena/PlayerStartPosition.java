package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Johannes Tomren Røsvik (@rosvik) on 3/25/2019.
 *
 * @author Johannes Tomren Røsvik (@rosvik)
 */
public class PlayerStartPosition {
    private String filePath;
    private TiledMap map;
    private ArrayList<Vector2> positions;

    /**
     * @param filePath Path to the .tmx file of the arena
     */
    public PlayerStartPosition(final String filePath) {
        this.filePath = filePath;
        getStartPositions();
    }

    /**
     * @return ArrayList with Vector2 elements that contain all the start positions
     */
    public ArrayList<Vector2> getStartPositions() {
        positions = new ArrayList<>();

        switch (this.filePath) {
            case "map2.tmx":
                positions.add(getVector2(5, 1));
                positions.add(getVector2(25, 1));
                positions.add(getVector2(3, 13));
                positions.add(getVector2(28, 13));
                break;
            case "map3.tmx":
                positions.add(getVector2(3, 3));
                positions.add(getVector2(18, 3));
                positions.add(getVector2(3, 16));
                positions.add(getVector2(18, 16));
                break;
            default:
                throw new RuntimeException("There are no known start positions for the file '" + filePath + "'.");
        }

        return positions;
    }

    /**
     * @param x The x value in meters
     * @param y The y value in meters
     * @return Vector2
     */
    private Vector2 getVector2(final float x, final float y) {
        Vector2 vector = new Vector2();
        vector.x = x;
        vector.y = y;

        return vector;
    }


    /**
     * @param i The player index
     * @return Vector2 To the player start position
     */
    public Vector2 getPlayerStartPosition(final int i) {
        return positions.get(i);
    }
}
