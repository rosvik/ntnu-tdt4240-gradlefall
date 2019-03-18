package no.ntnu.tdt4240.g17.server;

import com.badlogic.gdx.physics.box2d.Box2D;

import lombok.extern.slf4j.Slf4j;

/**
 * Main class for the server.
 */
@Slf4j
public final class ServerMain {
    /** Hidden constructor for utility classes. */
    private ServerMain() { }

    /**
     * Starts the server.
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        log.info("Server starting up");
        log.trace("Loading box2d native libraries...");
        Box2D.init();
        log.trace("Loaded box2d native libraries.");

        // TODO: read server parameters from .properties file or environment.
        // eg. bort number and bind adress (127.0.0.1 or 0.0.0.0).
        // TODO: Start server to listen for incoming clients.
        // TODO: Register for heartbeats/ping?
    }
}
