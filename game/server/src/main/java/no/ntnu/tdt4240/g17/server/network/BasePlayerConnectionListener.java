package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Just casts {@link Connection} to {@link PlayerConnection}
 * because kryonet isnt smart enough to provide generic types for its listeners.
 *
 * @author Kristian 'krissrex' Rekstad
 */
abstract class BasePlayerConnectionListener extends Listener {

    @Override
    public final void connected(final Connection connection) {
        connected((PlayerConnection) connection);
    }

    /**
     * @param connection the connection.
     * @see #connected(Connection)
     */
    public abstract void connected(PlayerConnection connection);


    @Override
    public final void received(final Connection connection, final Object object) {
        received((PlayerConnection) connection, object);
    }

    /**
     * @param connection the connection
     * @param object the data object
     * @see #received(Connection, Object)
     */
    public abstract void received(PlayerConnection connection, Object object);

    @Override
    public final void idle(final Connection connection) {
        idle((PlayerConnection) connection);
    }

    /**
     * @param connection the connection
     * @see #idle(Connection)
     */
    public abstract void idle(PlayerConnection connection);

    @Override
    public final void disconnected(final Connection connection) {
        disconnected((PlayerConnection) connection);
    }

    /**
     * @param connection the connection
     * @see #disconnected(Connection)
     */
    public abstract void disconnected(PlayerConnection connection);

}
