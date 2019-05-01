package no.ntnu.tdt4240.g17.server.game_engine.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.server.game_engine.player.NetworkedPlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerEntityFactory;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.CharacterBox2dBodyFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/30/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class SendUpdateMessageSystemTest {

    private Engine engine;
    private Entity testPlayer;
    private World world;
    private PlayerConnection playerConnectionMock;

    @BeforeAll
    static void init() {
        Box2D.init();
    }

    @BeforeEach
    void setUp() {
        engine = new Engine();
        world = new World(new Vector2(0f, -9.81f), true);
        final CharacterBox2dBodyFactory characterBox2dBodyFactory = new CharacterBox2dBodyFactory(world, 1);
        final PlayerEntityFactory playerEntityFactory = new PlayerEntityFactory(characterBox2dBodyFactory);

        testPlayer = playerEntityFactory.create("1", "testPlayer");
        playerConnectionMock = Mockito.mock(PlayerConnection.class);
        testPlayer.add(new NetworkedPlayerComponent(playerConnectionMock));
        engine.addEntity(testPlayer);
    }

    @AfterEach
    void tearDown() {
        world.dispose();
    }

    @Test
    void shouldCreateMessageWithState() {
        // Given
        final SendUpdateMessageSystem system = new SendUpdateMessageSystem(1, 10, SendUpdateMessageSystem.FAMILY);
        engine.addSystem(system);
        TransformComponent.MAPPER.get(testPlayer).getPosition().set(10f, 5f);

        // When
        final UpdateMessage message = system.createMessage();

        // Then
        assertThat(message, is(notNullValue()));
        assertThat(message.players, hasSize(1));
        assertThat(message.players.get(0).position.x, is(10f));
        assertThat(message.players.get(0).position.y, is(5f));
        System.out.println(message.toString());
    }

    @Test
    void shouldHandleUpdateWithoutCrash() {
        // Given
        final SendUpdateMessageSystem system = new SendUpdateMessageSystem(1, 10, SendUpdateMessageSystem.FAMILY);
        engine.addSystem(system);
        TransformComponent.MAPPER.get(testPlayer).getPosition().set(10f, 5f);
        Mockito.when(playerConnectionMock.isConnected()).thenReturn(true);

        // When
        system.updateInterval();

        // Then
        Mockito.verify(playerConnectionMock).isConnected();
        Mockito.verify(playerConnectionMock).sendTCP(Mockito.any(UpdateMessage.class));
    }
}