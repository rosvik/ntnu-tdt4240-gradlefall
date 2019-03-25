package no.ntnu.tdt4240.g17.server.network;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/25/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class PlayerConnectionListenerTest {


    @Test
    void shouldAddNewConnectionsToList() {
        // Given
        final ArrayList<PlayerConnection> connections = new ArrayList<>();
        final PlayerConnectionListener connectionListener = new PlayerConnectionListener(connections);
        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        connectionListener.connected(playerConnection);

        // Then
        assertThat(connections, hasItem(playerConnection));
    }

    @Test
    void shouldRemoveDisconnectedConnectionsFromList() {
        // Given
        final PlayerConnection playerConnection = new PlayerConnection();
        final ArrayList<PlayerConnection> connections = new ArrayList<>();
        connections.add(playerConnection);
        final PlayerConnectionListener connectionListener = new PlayerConnectionListener(connections);

        // When
        connectionListener.disconnected(playerConnection);

        // Then
        assertThat(connections, is(empty()));
    }
}