package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/25/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class BasePlayerConnectionListenerTest {

    @Test
    void shouldProxyConnectedCalls() {
        // Given

        final BasePlayerConnectionListener listener = Mockito.mock(BasePlayerConnectionListener.class);

        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.connected((Connection) playerConnection);

        // Then
        verify(listener).connected(playerConnection);
        verifyNoMoreInteractions(listener);
    }

    @Test
    void shouldProxyDisconnectedCalls() {
        // Given

        final BasePlayerConnectionListener listener = Mockito.mock(BasePlayerConnectionListener.class);

        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.disconnected((Connection) playerConnection);

        // Then
        verify(listener).disconnected(playerConnection);
        verifyNoMoreInteractions(listener);
    }

    @Test
    void shouldProxyIdleCalls() {
        // Given

        final BasePlayerConnectionListener listener = Mockito.mock(BasePlayerConnectionListener.class);

        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.idle((Connection) playerConnection);

        // Then
        verify(listener).idle(playerConnection);
        verifyNoMoreInteractions(listener);
    }

    @Test
    void shouldProxyReceivedCalls() {
        // Given

        final BasePlayerConnectionListener listener = Mockito.mock(BasePlayerConnectionListener.class);

        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.received((Connection) playerConnection, null);

        // Then
        verify(listener).received(playerConnection, null);
        verifyNoMoreInteractions(listener);
    }
}