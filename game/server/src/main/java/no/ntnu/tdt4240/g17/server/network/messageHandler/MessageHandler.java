package no.ntnu.tdt4240.g17.server.network.messageHandler;

import no.ntnu.tdt4240.g17.server.network.PlayerConnection;

/**
 * A handler for client-server messages of a specific message type.
 *
 * @param <M> Message class
 * @author Kristian 'krissrex' Rekstad
 */
public interface MessageHandler<M> {

    /** Handle the message of type M.
     * @param connection the connection the message came on
     * @param message the message object
     */
    void handle(PlayerConnection connection, M message);
}
