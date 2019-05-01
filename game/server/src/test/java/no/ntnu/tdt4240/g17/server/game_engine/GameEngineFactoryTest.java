package no.ntnu.tdt4240.g17.server.game_engine;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.server.game_session.Session;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/30/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class GameEngineFactoryTest {

    @Test
    void shouldCreateEngine() {
        // Given
        final GameEngineFactory factory = new GameEngineFactory();
        final Session session = Session.create(new ArrayList<>());

        // When
        final GameEngine gameEngine = factory.create(Arena.ARENA_2, new ArrayList<>());

        // Then
        assertThat(gameEngine, is(notNullValue()));
    }
}