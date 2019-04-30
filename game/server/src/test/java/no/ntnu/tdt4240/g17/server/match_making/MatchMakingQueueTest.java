package no.ntnu.tdt4240.g17.server.match_making;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/4/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class MatchMakingQueueTest {


    @Test
    void shouldMatchmakeFourPlayers() {
        // Given
        AtomicBoolean playersMatchmade = new AtomicBoolean(false);
        final MatchMakingQueue matchMakingQueue = new MatchMakingQueue(players -> playersMatchmade.set(players.length == 4));

        Supplier<PlayerConnection> createConnection = () -> Mockito.mock(PlayerConnection.class);

        // When
        matchMakingQueue.add(createConnection.get());
        matchMakingQueue.add(createConnection.get());
        matchMakingQueue.add(createConnection.get());
        matchMakingQueue.add(createConnection.get());


        // Then
        assertThat(playersMatchmade.get(), is(true));
    }

    @Test
    void shouldNotMatchmakeThreeRapidlyJoinedPlayers() {
        // Given
        AtomicBoolean playersMatchmade = new AtomicBoolean(false);
        final MatchMakingQueue matchMakingQueue = new MatchMakingQueue(players -> playersMatchmade.set(players.length == 4));

        Supplier<PlayerConnection> createConnection = () -> Mockito.mock(PlayerConnection.class);

        // When
        matchMakingQueue.add(createConnection.get());
        matchMakingQueue.add(createConnection.get());
        matchMakingQueue.add(createConnection.get());


        // Then
        assertThat(playersMatchmade.get(), is(false));
    }

    @Test
    void shouldMatchmakeThreePlayersAfterLongWait() {
        // Given
        AtomicBoolean playersMatchmade = new AtomicBoolean(false);
        final MatchMakingQueue matchMakingQueue = new MatchMakingQueue(players -> playersMatchmade.set(players.length > 1));

        Supplier<PlayerConnection> createConnection = () -> Mockito.mock(PlayerConnection.class);

        // When
        matchMakingQueue.add(createConnection.get());
        matchMakingQueue.add(createConnection.get());
        matchMakingQueue.currentTimeMillis = () -> System.currentTimeMillis() + MatchMakingQueue.MAX_QUEUE_WAIT_MILLISECONDS;
        matchMakingQueue.add(createConnection.get());


        // Then
        assertThat(playersMatchmade.get(), is(true));
    }
}