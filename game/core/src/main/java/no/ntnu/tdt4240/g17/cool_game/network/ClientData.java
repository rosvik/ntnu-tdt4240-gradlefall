package no.ntnu.tdt4240.g17.cool_game.network;

import java.util.List;

import no.ntnu.tdt4240.g17.common.network.game_messages.MatchmadeMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Projectile;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;

public class ClientData {


    private List<UpdateMessagePlayer> players;
    private List<Projectile> projectiles;

    public void recieve(Object message) {
//        if (message instanceof MatchmadeMessage) {
//            MatchmadeMessage matchmadeMessage = ((MatchmadeMessage) message);
//        }

        if (message instanceof UpdateMessage) {
            UpdateMessage updateMessage = ((UpdateMessage) message);

            players = updateMessage.players;
            projectiles = updateMessage.projectiles;
        }
    }
}
