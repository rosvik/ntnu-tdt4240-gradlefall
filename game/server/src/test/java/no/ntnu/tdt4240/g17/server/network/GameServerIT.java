package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.MessageClassLister;
import no.ntnu.tdt4240.g17.common.network.game_messages.PlayMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/1/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j @Disabled
class GameServerIT {


    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
    }

    @AfterEach
    void tearDown() {
        client.close();
        try {
            client.dispose();
        } catch (IOException ignored) { }
    }

    @Test @Disabled
    void clientServerCommunication() throws Exception {
        // FIXME: 4/1/2019 Doesn't work yet, a channel says it is closed
        // Setup
        final MessageHandlerDelegator handlerDelegator = new MessageHandlerDelegator();
        final int tcpPort = 5777;
        final GameServer gameServer = new GameServer(tcpPort, (severity, throwable) -> {
            fail(severity.name(), throwable);
        }, handlerDelegator);

        MessageClassLister.getMessageClasses().forEach(client.getKryo()::register);

        final AtomicBoolean clientDidConnect = new AtomicBoolean(false);

        client.addListener(new Listener() {
            @Override
            public void connected(final Connection connection) {
                log.debug("Client: connected");
                clientDidConnect.set(true);
            }

            @Override
            public void disconnected(final Connection connection) {
                log.debug("Client: disconnected");

            }

            @Override
            public void received(final Connection connection, final Object object) {
                log.debug("Client: received");
            }
        });

        final Thread serverThread = new Thread(gameServer, "Server");

        // Run
        serverThread.start();
        client.start();

        client.connect(10, "127.0.0.1", tcpPort);
        final PlayMessage playMessage = new PlayMessage();
        playMessage.gameMode = GameMode.HEADHUNTER;
        playMessage.playerName = "Test Player";
        playMessage.playerId = "1";
        client.sendTCP(playMessage);

        Thread.sleep(5000);

        // Stop
        gameServer.stop();
        client.stop();

        serverThread.join(4000);
        client.getUpdateThread().join(2000);


        // Verify
        assertThat(clientDidConnect.get(), is(true));
    }

    @Test
    void sendMessagesFromClient() throws Exception {
        // FIXME: 4/1/2019 Doesn't work yet, a channel says it is closed
        // Setup
        final int tcpPort = 5777;

        MessageClassLister.getMessageClasses().forEach(client.getKryo()::register);

        final AtomicBoolean clientDidConnect = new AtomicBoolean(false);

        client.addListener(new Listener() {
            @Override
            public void connected(final Connection connection) {
                log.debug("Client: connected");
                clientDidConnect.set(true);
            }

            @Override
            public void disconnected(final Connection connection) {
                log.debug("Client: disconnected");

            }

            @Override
            public void received(final Connection connection, final Object object) {
                log.debug("Client: received {}", object);
            }
        });


        // Run
        client.start();

        client.connect(10, "127.0.0.1", tcpPort);
        final PlayMessage playMessage = new PlayMessage();
        playMessage.gameMode = GameMode.HEADHUNTER;
        playMessage.playerName = "Test Player";
        playMessage.playerId = "1";
        client.sendTCP(playMessage);

        Thread.sleep(500);

        // Stop
        client.stop();

        client.getUpdateThread().join();


        // Verify
        assertThat(clientDidConnect.get(), is(true));
    }
}