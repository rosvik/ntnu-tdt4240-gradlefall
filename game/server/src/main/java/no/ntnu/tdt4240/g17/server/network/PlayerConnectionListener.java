package no.ntnu.tdt4240.g17.server.network;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Listens to events from player connections on a {@link GameServer}.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
class PlayerConnectionListener extends BasePlayerConnectionListener {

    private final List<PlayerConnection> connections;

    /**
     * Create a new listener. New connections are added to connections.
     * @param connections mutable list. Will be synchronized on.
     */
    PlayerConnectionListener(final List<PlayerConnection> connections) {
        this.connections = connections;
    }

    @Override
    public void connected(final PlayerConnection connection) {
        // Runs on same thread as Server#update
        synchronized (connections) {
            connections.add(connection);
        }
        log.debug("Client {} connected. Network RTT: {}", connection.getID(), connection.getReturnTripTime());
        log.info("Currently have {} connections.", connections.size());
    }

    @Override
    public void received(final PlayerConnection connection, final Object object) {
        // Runs on same thread as Server#update
        // TODO: 3/22/2019 Implement this
        /*
        Should probably do it like this:
        find if the player is in a game.
            if so, find the thread that handles the game engine.
            Send the message to that thread.
        if not in a game:
            send to a generic handler/executor for inactive clients
        if entering matchmaking:
            send to matchmaking thread
         */
    }

    @Override
    public void idle(final PlayerConnection connection) {
    }

    @Override
    public void disconnected(final PlayerConnection connection) {
        // unspecified thread
        // FIXME: 3/22/2019 only remove from connections or similiar if the user intended to disconnect?
        // Do connection interrupts come here?
        synchronized (connections) {
            connections.remove(connection);
        }
        log.debug("Client {} disconnected", connection.getID());
        log.info("Currently have {} connections.", connections.size());
    }
}
