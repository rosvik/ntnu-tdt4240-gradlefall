package no.ntnu.tdt4240.g17.server.game_session;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import no.ntnu.tdt4240.g17.server.game_engine.GameEngine;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerEntityFactory;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Kristian 'krissrex' Rekstad on 5/1/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class SessionTest {

    @Test
    void shouldCreateEntitiesForAllPlayers() {
        // Given
        final Player player1 = new Player("1", Mockito.mock(PlayerConnection.class));
        final Player player2 = new Player("2", Mockito.mock(PlayerConnection.class));
        final List<Player> players = Arrays.asList(player1, player2);

        // When
        final Session session = Session.create(players);
        final GameEngine gameEngine = session.getGameEngine();
        final ImmutableArray<Entity> playerEntities = gameEngine.getEcsEngine().getEntitiesFor(PlayerEntityFactory.FAMILY);

        // Then
        assertThat(playerEntities.size(), is(players.size()));
    }
}