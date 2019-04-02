package no.ntnu.tdt4240.g17.cool_game.network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.time.Duration;

import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Johannes Tomren Røsvik (@rosvik) on 4/2/2019.
 *
 * @author Johannes Tomren Røsvik (@rosvik)
 */
class GameClientTest {


    private GameClient gameClient;

    @BeforeEach
    void create() {
        ClientData clientData = new ClientData();
        gameClient = new GameClient(5777, clientData);
    }


    @Test
    void run() {
        assertThrows(AssertionFailedError.class, () ->
            assertTimeoutPreemptively(Duration.ofMillis(2000), () -> gameClient.run())
        );
    }

    @Test
    void send() {
        ControlsMessage controlsMessage = new ControlsMessage();

        assertDoesNotThrow(() -> gameClient.send(controlsMessage));
    }

    @AfterEach
    void stop() {
        gameClient.stop();
    }

}