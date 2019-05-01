package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.Test;

import java.util.List;

import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by Kristian 'krissrex' Rekstad on 5/1/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class ArenaTmxReaderTest {


    @Test
    void shouldReadTmxFiles() {
        // Given
        final ArenaTmxReader reader = new ArenaTmxReader();

        // When
        final List<Vector2> tiles = reader.getTiles(Arena.ARENA_2);

        // Then
        assertThat(tiles, is(not(empty())));
        assertThat(tiles.size(), is(lessThan(20*32)));
        assertThat(tiles.get(0).x, is(0f));
        assertThat(tiles.get(0).y, is(19f));
        System.out.println(tiles);
        assertThat(tiles.get(8).x, is(not(8f))); // Hole in the arena
    }
}