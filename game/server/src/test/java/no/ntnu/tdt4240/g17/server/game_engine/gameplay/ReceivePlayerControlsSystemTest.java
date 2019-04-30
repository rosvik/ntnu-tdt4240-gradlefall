package no.ntnu.tdt4240.g17.server.game_engine.gameplay;

import com.badlogic.ashley.core.Entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/30/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class ReceivePlayerControlsSystemTest {


    @Test
    void shouldProcessMessageWithoutCrash() {
        // Given
        final ArrayDeque<ControlsMessage> controllMessageQueue = new ArrayDeque<>();
        final ReceivePlayerControlsSystem system = new ReceivePlayerControlsSystem(ReceivePlayerControlsSystem.PLAYER_CONTROLS_FAMILY, 0,
                controllMessageQueue);
        final ControlsMessage message = new ControlsMessage();
        final HashMap<String, Entity> playerHashMap = new HashMap<>();

        final Entity playerEntitiy = new Entity();
        message.playerId = "1";
        playerHashMap.put(message.playerId, playerEntitiy);

        // When
        controllMessageQueue.add(message);
        system.processMessage(playerHashMap, message);

        // Then
    }
}