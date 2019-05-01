package no.ntnu.tdt4240.g17.server.match_making;

import com.badlogic.gdx.math.Vector2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.MatchmadeMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.MatchmadeMessagePlayer;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.PlayerSkin;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.server.game_arena.ArenaUtil;
import no.ntnu.tdt4240.g17.server.game_arena.PlayerStartPosition;
import no.ntnu.tdt4240.g17.server.game_session.Player;
import no.ntnu.tdt4240.g17.server.game_session.Session;
import no.ntnu.tdt4240.g17.server.network.PlayerConnection;
import no.ntnu.tdt4240.g17.server.network.PlayerState;

/**
 * Creates a new session when players are matchmade.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class CreateSessionOnMatchmadeListener implements MatchMakingQueue.OnPlayersMatchmadeListener {

    @Override
    public void onMatchmade(final PlayerConnection[] playerConnections) {
        log.info("Creating session for {} players.", playerConnections.length);
        ArrayList<Player> players = new ArrayList<>(playerConnections.length);
        for (int i = 0; i < playerConnections.length; i++) {
            final PlayerConnection playerConnection = playerConnections[i];
            final Player player = new Player(playerConnection.getId(), playerConnection);
            player.setPlayerName("Player " + i); // FIXME: 4/4/2019 Make names player customizable.
            players.add(player);
        }
        final Session session = Session.create(players);
        final Arena arena = session.getArena();

        final MatchmadeMessage matchmadeMessage = createMatchmadeMessage(players, arena, session.getGameMode());

        notifyPlayers(playerConnections, matchmadeMessage);
        session.startEngine();
    }

    /**
     * Creates a message for matchmaking, from the given players and arena.
     * @param players the players in the session
     * @param arena the chosen arena for the match.
     * @param gameMode the chosen game mode
     * @return a message that can be sent to clients.
     */
    @NotNull
    private MatchmadeMessage createMatchmadeMessage(final ArrayList<Player> players, final Arena arena,
                                                    final GameMode gameMode) {
        final MatchmadeMessage matchmadeMessage = new MatchmadeMessage();
        matchmadeMessage.arena = arena;
        matchmadeMessage.gameMode = gameMode;
        matchmadeMessage.players = new ArrayList<>();

        final ArrayList<Vector2> startPositions = new PlayerStartPosition(ArenaUtil.getFilePathFor(arena))
                .getStartPositions();

        for (int i = 0; i < players.size(); i++) {
            final Player player = players.get(i);
            final MatchmadeMessagePlayer matchmadeMessagePlayer = new MatchmadeMessagePlayer();
            matchmadeMessagePlayer.playerId = player.getId();
            matchmadeMessagePlayer.playerName = player.getPlayerName();
            matchmadeMessagePlayer.playerSkin = PlayerSkin.SKIN_KNIGHT; // FIXME: 4/4/2019 Let players pick skin
            final Vector2 position = startPositions.get(i);
            matchmadeMessagePlayer.position = new Position(position.x, position.y);
            matchmadeMessage.players.add(matchmadeMessagePlayer);
        }
        return matchmadeMessage;
    }

    /**
     * Send a message to the players.
     *
     * @param connections the connections
     * @param message     the messagee to send
     */
    private static void notifyPlayers(final PlayerConnection[] connections, final MatchmadeMessage message) {
        log.info("Notifying {} players that they are matchmade.", connections.length);
        for (PlayerConnection connection : connections) {
            connection.sendTCP(message);
            connection.setState(PlayerState.IN_GAME);
        }
    }
}
