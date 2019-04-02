package no.ntnu.tdt4240.g17.cool_game.network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class GameClientTest {


    private GameClient gameClient;

    @BeforeEach
    void create() {
        ClientData clientData = new ClientData();
        gameClient = new GameClient(5777, clientData);
        gameClient.run();
    }

    @Test
    void send() {

        gameClient.run();
        gameClient.stop();
    }

    @AfterEach
    void stop() {
        gameClient.stop();
    }

}