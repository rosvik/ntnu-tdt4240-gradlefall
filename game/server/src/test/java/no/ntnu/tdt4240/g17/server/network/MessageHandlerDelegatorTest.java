package no.ntnu.tdt4240.g17.server.network;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import no.ntnu.tdt4240.g17.server.network.messageHandler.MessageHandler;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/25/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class MessageHandlerDelegatorTest {

    @Test
    void shouldRegisterHandler() {
        // Given
        final MessageHandlerDelegator handlerDelegator = new MessageHandlerDelegator();

        // When
        handlerDelegator.registerHandler((connection, message) -> {}, String.class);

        // Then
        assertThat(handlerDelegator.handlerCount(), is(1));
    }

    @Test
    void shouldUnregisterHandler() {
        // Given
        final MessageHandlerDelegator handlerDelegator = new MessageHandlerDelegator();

        // When
        handlerDelegator.registerHandler((connection, message) -> {}, String.class);
        handlerDelegator.unregisterHandler(String.class);

        // Then
        assertThat(handlerDelegator.handlerCount(), is(0));
    }

    @Test
    void shouldDelegateMessages() {
        // Given
        final MessageHandlerDelegator handlerDelegator = new MessageHandlerDelegator();
        AtomicBoolean stringHandlerCalled = new AtomicBoolean(false);
        AtomicBoolean integerHandlerCalled = new AtomicBoolean(false);
        final MessageHandler<String> stringHandler = (connection, str) -> { stringHandlerCalled.set(true); };
        final MessageHandler<Integer> integerHandler = (connection, integer) -> {integerHandlerCalled.set(true); };

        handlerDelegator.registerHandler(integerHandler, Integer.class);
        handlerDelegator.registerHandler(stringHandler, String.class);

        final PlayerConnection connection = new PlayerConnection();
        // When

        handlerDelegator.handleMessage(connection, "test");

        // Then
        assertTrue(stringHandlerCalled.get(), "String handler not called");
        assertFalse(integerHandlerCalled.get(), "String handler not called");
    }

    @Test
    void shouldThrowOnDoubleRegister() {
        // Given
        final MessageHandlerDelegator handlerDelegator = new MessageHandlerDelegator();

        // When
        handlerDelegator.registerHandler((connection, message) -> {}, String.class);

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            handlerDelegator.registerHandler((connection, message) -> {}, String.class);
        });
    }

}