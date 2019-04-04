package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.math.Rectangle;

import org.junit.jupiter.api.Test;

import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/1/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class ArenaBoundsTest {

    @Test
    void shouldReturnBounds() {
        // Given

        // When
        final Rectangle bounds = ArenaUtil.getBoundsFor(Arena.ARENA_2);

        // Then
        assertThat(bounds, is(notNullValue()));
    }
}