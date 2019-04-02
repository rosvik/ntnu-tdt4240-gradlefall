package no.ntnu.tdt4240.g17.cool_game.network;

import java.util.List;

import lombok.Getter;
import no.ntnu.tdt4240.g17.common.network.game_messages.GameOverMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.IntermediaryEndMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.MatchmadeMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameOverMessagePlayer;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.MatchmadeMessagePlayer;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Player;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Projectile;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;

/**
 * Created by Johannes Tomren Røsvik (@rosvik) on 4/1/2019.
 *
 * @author Johannes Tomren Røsvik (@rosvik)
 */
@Getter
public class ClientData {

    private List<Projectile> projectiles;
    private Arena arena;

    private List<Player> intermediaryEndPlayers;
    private List<UpdateMessagePlayer> updatePlayers;
    private List<MatchmadeMessagePlayer> matchmadePlayers;
    private List<GameOverMessagePlayer> gameOverPlayers;

    private GameMode gameMode;
    private List<String> winnerIds;


    /**
     * Handle a message from the server and save it.
     *
     * @param message The message received from the server
     */
    public void receive(final Object message) {
        if (message instanceof MatchmadeMessage) {
            MatchmadeMessage matchmadeMessage = ((MatchmadeMessage) message);

            arena = matchmadeMessage.arena;
            matchmadePlayers = matchmadeMessage.players;
        }

        if (message instanceof UpdateMessage) {
            UpdateMessage updateMessage = ((UpdateMessage) message);

            updatePlayers = updateMessage.players;
            projectiles = updateMessage.projectiles;
        }

        if (message instanceof IntermediaryEndMessage) {
            IntermediaryEndMessage intermediaryEndMessage = ((IntermediaryEndMessage) message);

            gameMode = intermediaryEndMessage.gameMode;
            arena = intermediaryEndMessage.nextArena;
            intermediaryEndPlayers = intermediaryEndMessage.players;
        }

        if (message instanceof GameOverMessage) {
            GameOverMessage gameOverMessage = ((GameOverMessage) message);

            gameMode = gameOverMessage.gameMode;
            gameOverPlayers = gameOverMessage.gamePlayers;
            winnerIds = gameOverMessage.winnerIds;
        }
    }

    /**
     * Get a player with a given ID.
     *
     * @param id The ID of the player
     * @return UpdateMessagePlayer The player with the given ID or null
     */
    public UpdateMessagePlayer getPlayerById(final String id) {
        if (updatePlayers == null) {
            return null;
        }

        for (UpdateMessagePlayer updatePlayer : updatePlayers) {
            if (updatePlayer.playerId.equals(id)) {
                return updatePlayer;
            }
        }

        return null;
    }

    /**
     * Get a player with a given ID.
     *
     * @param id The ID of the player
     * @return UpdateMessagePlayer The player with the given ID or null
     */
    public Projectile getProjectileById(final String id) {
        if (projectiles == null) {
            return null;
        }

        for (Projectile projectile : projectiles) {
            if (projectile.projectileId.equals(id)) {
                return projectile;
            }
        }

        return null;
    }
}