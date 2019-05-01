package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Stream;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/25/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class BasePlayerConnectionListenerTest {

    private TestablePlayerConnectionListener listener;

    @BeforeEach
    void setUp() {
        listener = new TestablePlayerConnectionListener();
    }

    @Test
    void shouldProxyConnectedCalls() {
        // Given
        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.connected((Connection) playerConnection);

        // Then
        assertTrue(listener.connectedCalled);
        assertTrue(listener.onlyOneMethodCalled());
    }

    @Test
    void shouldProxyDisconnectedCalls() {
        // Given
        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.disconnected((Connection) playerConnection);

        // Then
        assertTrue(listener.disconnectedCalled);
        assertTrue(listener.onlyOneMethodCalled());
    }

    @Test
    void shouldProxyIdleCalls() {
        // Given
        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.idle((Connection) playerConnection);

        // Then
        assertTrue(listener.idleCalled);
        assertTrue(listener.onlyOneMethodCalled());
    }

    @Test
    void shouldProxyReceivedCalls() {
        // Given
        final PlayerConnection playerConnection = new PlayerConnection();

        // When
        listener.received((Connection) playerConnection, null);

        // Then
        assertTrue(listener.receivedCalled);
        assertTrue(listener.onlyOneMethodCalled());
    }

    private static class TestablePlayerConnectionListener extends BasePlayerConnectionListener {
        public boolean connectedCalled = false;
        public boolean idleCalled = false;
        public boolean receivedCalled = false;
        public boolean disconnectedCalled = false;

        public boolean onlyOneMethodCalled() {
            return Stream.of(connectedCalled, idleCalled, receivedCalled, disconnectedCalled)
                    .filter(it -> it)
                    .count() == 1;
        }

        @Override
        public void connected(final PlayerConnection connection) {
            connectedCalled = true;
        }

        @Override
        public void received(final PlayerConnection connection, final Object object) {
            receivedCalled = true;
        }

        @Override
        public void idle(final PlayerConnection connection) {
            idleCalled = true;
        }

        @Override
        public void disconnected(final PlayerConnection connection) {
            disconnectedCalled = true;
        }
    }
}