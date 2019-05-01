package no.ntnu.tdt4240.g17.server.game_engine.gameplay;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.HashMap;

import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;

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
        // no crash
    }

    @Test
    void shouldProcessJump() {
        // Given
        final ArrayDeque<ControlsMessage> controllMessageQueue = new ArrayDeque<>();
        final ReceivePlayerControlsSystem system = new ReceivePlayerControlsSystem(ReceivePlayerControlsSystem.PLAYER_CONTROLS_FAMILY, 0,
                controllMessageQueue);
        final ControlsMessage message = new ControlsMessage();
        message.jump = true;
        final HashMap<String, Entity> playerHashMap = new HashMap<>();

        final Entity playerEntitiy = new Entity();
        final Body bodyMock = Mockito.mock(Body.class);
        Mockito.when(bodyMock.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        playerEntitiy.add(new Box2dBodyComponent(bodyMock));
        message.playerId = "1";
        playerHashMap.put(message.playerId, playerEntitiy);

        // When
        controllMessageQueue.add(message);
        system.processMessage(playerHashMap, message);

        // Then
        // What to assert? bodyMock got some changes, but we don't know what.
    }

    @Test
    void shouldProcessMove() {
        // Given
        final ArrayDeque<ControlsMessage> controllMessageQueue = new ArrayDeque<>();
        final ReceivePlayerControlsSystem system = new ReceivePlayerControlsSystem(ReceivePlayerControlsSystem.PLAYER_CONTROLS_FAMILY, 0,
                controllMessageQueue);
        final ControlsMessage message = new ControlsMessage();
        message.moveSpeed = 1;
        final HashMap<String, Entity> playerHashMap = new HashMap<>();

        final Entity playerEntitiy = new Entity();
        final Body bodyMock = Mockito.mock(Body.class);
        Mockito.when(bodyMock.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        playerEntitiy.add(new Box2dBodyComponent(bodyMock));
        message.playerId = "1";
        playerHashMap.put(message.playerId, playerEntitiy);

        // When
        controllMessageQueue.add(message);
        system.processMessage(playerHashMap, message);

        // Then
        Mockito.verify(bodyMock).setLinearVelocity(Mockito.anyFloat(), Mockito.anyFloat());
    }
}