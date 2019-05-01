package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;

/**
 * Utility class for arena sizes.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class ArenaUtil {

    /** Utility class. */
    private ArenaUtil() { }

    private static final ArenaTmxReader TILE_READER = new ArenaTmxReader();

    /**
     * Get the bounds for a arena.
     * @param arena the arena to get bounds for.
     * @return the bounds.
     */
    public static Rectangle getBoundsFor(final Arena arena) {
        switch (arena) {
            default:
            case ARENA_2: return new Rectangle(0, 0, 32, 20);
        }
    }

    /**
     * @param arena the arena
     * @return the filename for an arena
     */
    public static String getFilePathFor(final Arena arena) {
        switch (arena) {
            default:
            case ARENA_2: return "map2.tmx";
        }
    }

    /**
     * Get the tiles in a map.
     * @param arena the arena to read tiles form
     * @return a list of (x,y) positions of tiles' bottom left corner.
     */
    public static List<Vector2> getTileCoordinatesFor(final Arena arena) {
        return TILE_READER.getTiles(arena);
    }
}
