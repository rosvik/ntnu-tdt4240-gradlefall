package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.FrameworkMessage;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.network.messageHandler.MessageHandler;

/**
 * Delegates incoming messages to handlers.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class MessageHandlerDelegator {

    private final Map<Class, MessageHandler> handlers = new HashMap<>();

    /**
     * Register a handler for the given message class.
     *
     * @param handler      the message handler
     * @param messageClass the message class
     * @param <M>          message class type
     */
    public <M> void registerHandler(final MessageHandler<M> handler, final Class<M> messageClass) {
        if (handlers.containsKey(messageClass)) {
            final String errorMessage = String.format("Tried to register handler \"%s\","
                            + "but there is already a handler registered for \"%s\": \n\"%s\"",
                    handler.getClass().getCanonicalName(),
                    messageClass.getCanonicalName(),
                    handlers.get(messageClass).getClass().getCanonicalName());
            throw new IllegalArgumentException(errorMessage);
        }

        handlers.put(messageClass, handler);
    }

    /**
     * Removes the handler for the given message class.
     * @param messageClass the message class to remove the handler for.
     */
    public void unregisterHandler(final Class messageClass) {
        handlers.remove(messageClass);
    }

    /**
     * Sends the message to registered receivers.
     *
     * @param connection the connection the message came from
     * @param message the message to handle
     */
    public void handleMessage(final PlayerConnection connection, final Object message) {
        final MessageHandler messageHandler = handlers.get(message.getClass());
        if (messageHandler != null) {
            messageHandler.handle(connection, message);
        } else {
            if (!(message instanceof FrameworkMessage.KeepAlive)) {
                log.warn("Unhandled message: {}", message);
            }
        }
    }

    /**
     * Used for testing.
     * @return the amount of handlers.
     */
    int handlerCount() {
        return handlers.size();
    }
}
