package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

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
        setStartPositions();
    }

    /** Sets all initial positions. */
    private void setStartPositions() {
        positions = new ArrayList<>();

        switch (this.filePath) {
            case "map2.tmx":
                positions.add(new Vector2(5, 1));
                positions.add(new Vector2(25, 1));
                positions.add(new Vector2(3, 13));
                positions.add(new Vector2(28, 13));
                break;
            case "map3.tmx":
                positions.add(new Vector2(3, 3));
                positions.add(new Vector2(18, 3));
                positions.add(new Vector2(3, 16));
                positions.add(new Vector2(18, 16));
                break;
            default:
                throw new RuntimeException("There are no known start positions for the file '" + filePath + "'.");
        }
    }

    /**
     * @return ArrayList with Vector2 elements that contain all the start positions
     */
    public List<Vector2> getStartPositions() {
        return positions;
    }

    /**
     * @param i The player index
     * @return Vector2 To the player start position
     */
    public Vector2 getPlayerStartPosition(final int i) {
        return positions.get(i);
    }
}
