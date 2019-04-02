package no.ntnu.tdt4240.g17.cool_game.network;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.tdt4240.g17.common.network.game_messages.GameOverMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.IntermediaryEndMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.MatchmadeMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ClientDataTest {

    ClientData clientData;

    @BeforeEach
    void init() {
        clientData = new ClientData();
    }

    @Test
    void receiveMatchmadeMessage() {
        MatchmadeMessage mock = Mockito.mock(MatchmadeMessage.class);

        assertDoesNotThrow(() -> clientData.receive(mock));
    }

    @Test
    void receiveUpdateMessage() {
        UpdateMessage mock = Mockito.mock(UpdateMessage.class);

        assertDoesNotThrow(() -> clientData.receive(mock));
    }

    @Test
    void receiveIntermediaryEndMessage() {
        IntermediaryEndMessage mock = Mockito.mock(IntermediaryEndMessage.class);

        assertDoesNotThrow(() -> clientData.receive(mock));
    }

    @Test
    void receiveGameOverMessage() {
        GameOverMessage mock = Mockito.mock(GameOverMessage.class);

        assertDoesNotThrow(() -> clientData.receive(mock));
    }

    @Test
    void getPlayerById() {

        // Null when empty
        assertNull(clientData.getPlayerById("empty"));

        UpdateMessage updateMessage = new UpdateMessage();
        UpdateMessagePlayer updateMessagePlayer = new UpdateMessagePlayer();
        List<UpdateMessagePlayer> updateMessagePlayers = new ArrayList<>();

        updateMessagePlayer.playerId = "ABC";
        updateMessagePlayers.add(updateMessagePlayer);
        updateMessage.players = updateMessagePlayers;
        clientData.receive(updateMessage);

        // Valid call
        assertThat(clientData.getPlayerById("ABC"), isA(UpdateMessagePlayer.class));

        // Null with wrong ID
        assertNull(clientData.getPlayerById("Nonexistent"));
    }
}