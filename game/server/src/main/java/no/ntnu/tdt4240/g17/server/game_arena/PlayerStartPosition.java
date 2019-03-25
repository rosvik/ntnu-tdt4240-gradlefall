package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import no.ntnu.tdt4240.g17.server.game_session.Player;

public class PlayerStartPosition {
    public float positionX;
    public float positionY;
    private String filePath;
    private TiledMap map;

    public PlayerStartPosition(final String filePath) {
        this.filePath = filePath;
        map = new TmxMapLoader().load(filePath);
    }

    public getStartPositions() {
        MapLayer mapLayer = map.getLayers().get("Players");
        mapLayer
    }
}
