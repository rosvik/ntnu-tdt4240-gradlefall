package no.ntnu.tdt4240.g17.server.network.messageHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

/**
 * Created by Kristian 'krissrex' Rekstad on 5/1/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class ControlsMessageEventBus {
    private static final ControlsMessageEventBus SINGLETON_INSTANCE = new ControlsMessageEventBus();

    /** @return the event bus singleton.*/
    public static ControlsMessageEventBus getInstance() {
        return SINGLETON_INSTANCE;
    }

    private final ArrayList<Subscription> subscribers = new ArrayList<>();

    /** Singleton class. */
    private ControlsMessageEventBus() { }

    /**
     * Emit new ControlsMessages.
     * @param fromConnection the connection the message was received on.
     * @param message the new message
     */
    public void emit(final PlayerConnection fromConnection, final ControlsMessage message) {
        for (final Subscription subscriber : subscribers) {
            if (subscriber.playerFilter.test(fromConnection)) {
                subscriber.consumer.accept(message, fromConnection);
            }
        }
    }

    /**
     * Subscribe to events.
     * @param connectionFilter filter messages based on connection, like {@link PlayerConnection#getId()}.
     * @param consumer Process messages. Can run on an arbitrary thread!
     * @return a subscription. Use this to unsubscribe with {@link Subscription#unsubscribe()}.
     */
    @NotNull
    public Subscription subscribe(final Predicate<PlayerConnection> connectionFilter,
                          final BiConsumer<ControlsMessage, PlayerConnection> consumer) {
        final Subscription subscription = new Subscription(consumer, connectionFilter, this);
        subscribers.add(subscription);
        return subscription;
    }

    /** A subscription to an event bus. */
    public static final class Subscription {
        private final BiConsumer<ControlsMessage, PlayerConnection> consumer;
        private final Predicate<PlayerConnection> playerFilter;
        private final ControlsMessageEventBus bus;

        /**
         * @param consumer the event consumer
         * @param playerFilter the filter for events
         * @param bus the bus to unsubscribe from in {@link #unsubscribe()}
         */
        private Subscription(@NotNull final BiConsumer<ControlsMessage, PlayerConnection> consumer,
                            final Predicate<PlayerConnection> playerFilter,
                             @NotNull final ControlsMessageEventBus bus) {
            this.consumer = consumer;
            this.playerFilter = playerFilter;
            this.bus = bus;
        }

        /** Unsubscribes from events. */
        public void unsubscribe() {
            bus.subscribers.remove(this);
        }
    }
}
