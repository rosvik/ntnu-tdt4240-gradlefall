package no.ntnu.tdt4240.g17.cool_game.screens.loading;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import lombok.Setter;
import no.ntnu.tdt4240.g17.common.network.game_messages.MatchmadeMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.PlayMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;

/**
 * A model for matchmaking / loading screen.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class LoadingModel implements Disposable {

    private final Listener matchmadeListener;
    private Client networkClientInstance;
    private boolean isBeingMatchmade = false;
    private boolean hasBeenMatchmade = false;

    @Setter @Nullable
    private Consumer<MatchmadeMessage> onMatchmadeListener = null;

    /**
     * Creates a new instance of the model.
     * @param networkClientInstance an instance of the network client.
     */
    public LoadingModel(@NotNull final Client networkClientInstance) {
        this.networkClientInstance = networkClientInstance;

        matchmadeListener = new Listener() {
            @Override
            public void received(final Connection connection, final Object message) {
                if (message instanceof MatchmadeMessage) {
                    if (onMatchmadeListener != null) {
                        isBeingMatchmade = false;
                        hasBeenMatchmade = true;
                        onMatchmadeListener.accept((MatchmadeMessage) message);
                    }
                }
            }
        };
        networkClientInstance.addListener(matchmadeListener);
    }

    /**
     * @return true if connected to the server.
     */
    public boolean isConnected() {
        return networkClientInstance.isConnected();
    }

    /** @return true if the player is currently in matchmaking queue. */
    public boolean isMatchmaking() {
        return isBeingMatchmade;
    }

    /** @return true if matchmaking is done. */
    public boolean hasBeenMatchmade() {
        return hasBeenMatchmade;
    }

    /**
     * Tell the server to put the player into matchmaking.
     * @return true if matchmaking started, false otherwise.
     */
    public boolean startMatchmaking() {
        if (networkClientInstance.isConnected()) {
            final PlayMessage playMessage = new PlayMessage();
            playMessage.gameMode = GameMode.HEADHUNTER; // FIXME: 4/30/2019 read this from player selection.
            playMessage.playerId = null; // FIXME: 4/30/2019 send or get this from server.
            playMessage.playerName = "Unset name"; // FIXME: 4/30/2019 read this from prefs
            networkClientInstance.sendTCP(playMessage);
            isBeingMatchmade = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void dispose() {
        networkClientInstance.removeListener(matchmadeListener);
    }
}
