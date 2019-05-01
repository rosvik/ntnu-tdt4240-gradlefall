package no.ntnu.tdt4240.g17.server.match_making;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

/**
 * Matches players together.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class MatchMakingQueue {

    private static final int MATCH_PLAYER_SIZE = 4;
    static final int MAX_QUEUE_WAIT_MILLISECONDS = 8000;

    private List<QueueEntry> queue = new ArrayList<>();
    private final OnPlayersMatchmadeListener playersMatchmadeListener;

    Supplier<Long> currentTimeMillis = System::currentTimeMillis;

    /**
     * Create a new matchmaking queue.
     *
     * @param playersMatchmadeListener a listener that will be notified when players are matchmade
     */
    public MatchMakingQueue(final OnPlayersMatchmadeListener playersMatchmadeListener) {
        this.playersMatchmadeListener = playersMatchmadeListener;

        final boolean isDaemon = true;
        final Timer matchMakingTimer = new Timer("MatchMakingTimer", isDaemon);

        final int initialDelayMs = 2000;
        final int intervalMs = 1000;
        matchMakingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tryMatchmaking();
            }
        }, initialDelayMs, intervalMs);
    }

    /**
     * Add a new player to the queue.
     *
     * @param queuedItem the player connection to be added
     */
    public void add(final PlayerConnection queuedItem) {
        final QueueEntry queueEntry = new QueueEntry(queuedItem, currentTimeMillis.get());
        queue.add(queueEntry);

        final RemoveFromQueueOnDisconnectListener onDisconnectedListener =
                new RemoveFromQueueOnDisconnectListener(queueEntry, queuedItem);
        queueEntry.setOnDisconnectedListener(onDisconnectedListener);
        queuedItem.addListener(onDisconnectedListener);

        log.info("Players in matchmaking queue: {}", queue.size());
        tryMatchmaking();
    }

    /**
     * Called after a new connection has been added.
     */
    protected synchronized void tryMatchmaking() {
        if (this.queue.isEmpty()) {
            return;
        }

        while (queue.size() >= MATCH_PLAYER_SIZE) {
            final PlayerConnection[] matchmadePlayers = getConnections(queue, MATCH_PLAYER_SIZE);
            playersMatchmadeListener.onMatchmade(matchmadePlayers);
        }

        //Todo call this every second or so while a player is in the queue
        final int remainingSize = queue.size();
        if (remainingSize > 1) {
            long maxWait = currentTimeMillis.get() - queue.get(0).addedAt;
            if (maxWait >= MAX_QUEUE_WAIT_MILLISECONDS) {
                final PlayerConnection[] matchmadePlayers = getConnections(queue, remainingSize);
                log.info("Matchmade {} players from waiting too long", matchmadePlayers.length);
                playersMatchmadeListener.onMatchmade(matchmadePlayers);
            }
        }
    }

    /**
     * Map and group a subset of the first items in the queue.
     *
     * @param queue the queue to get items from
     * @param count the amount of items to get
     * @return a list of connections
     */
    private static PlayerConnection[] getConnections(final List<QueueEntry> queue, final int count) {
        final PlayerConnection[] matchmadePlayers = new PlayerConnection[count];
        for (int i = 0; i < count; i++) {
            final QueueEntry queueEntry = queue.remove(0);
            matchmadePlayers[i] = queueEntry.playerConnection;
            queueEntry.playerConnection.removeListener(queueEntry.onDisconnectedListener);
        }
        return matchmadePlayers;
    }

    /**
     * Entry in the queue.
     */
    private static final class QueueEntry {
        @Getter
        private final PlayerConnection playerConnection;

        @Getter
        private final long addedAt;

        /**
         * Stores the listener that is connected to {@link #playerConnection}.
         */
        @Getter
        @Setter
        private RemoveFromQueueOnDisconnectListener onDisconnectedListener;

        /**
         * Create a new queue entry.
         *
         * @param playerConnection the connection for the player
         * @param addedAt          timestamp when the player was added to the queue
         */
        QueueEntry(final PlayerConnection playerConnection, final long addedAt) {
            this.playerConnection = playerConnection;
            this.addedAt = addedAt;
        }
    }

    /**
     * Listener for when players are matchmade.
     */
    @FunctionalInterface
    public interface OnPlayersMatchmadeListener {
        /**
         * Called when players are matchmade.
         *
         * @param players the matchmade players
         */
        void onMatchmade(PlayerConnection[] players);
    }

    /** Removes a queued player when disconnecting.
     * This listener automatically removes itself as a listener when disconnected.
     */
    private class RemoveFromQueueOnDisconnectListener extends Listener {
        private final QueueEntry queueEntry;
        private final PlayerConnection playerConnection;

        /**
         * @param queueEntry the entry to remove on disconnect
         * @param playerConnection the connection to listen to disconnects
         */
        RemoveFromQueueOnDisconnectListener(final QueueEntry queueEntry, final PlayerConnection playerConnection) {
            this.queueEntry = queueEntry;
            this.playerConnection = playerConnection;
        }

        @Override
        public void disconnected(final Connection connection) {
            log.info("Player {} disconnected! Removed from queue.", ((PlayerConnection) connection).getId());
            queue.remove(queueEntry);
            playerConnection.removeListener(this);
        }
    }
}
