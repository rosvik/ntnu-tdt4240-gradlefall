package no.ntnu.tdt4240.g17.common.network;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.CancelMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.GameOverMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.IntermediaryEndMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.IntermediaryStartMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.MatchmadeMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.PlayMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Block;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Effect;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.EffectType;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameOverMessagePlayer;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.IntermediaryStartMessagePlayer;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.MatchmadeMessagePlayer;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Player;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.PlayerSkin;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Powerup;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.PowerupType;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Projectile;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.ProjectileType;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.SoundEffect;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.UpdateMessagePlayer;

/**
 * Lists all message classes. This is needed for Kryo serialization.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class MessageClassLister {
    /** Utility class. */
    private MessageClassLister() { }

    /** Singleton pattern that delays class loading until actually used. */
    private static final class MessageClassListHolder {
        /** Singleton, immutable list. */
        private static final List<Class> CLASSES_SINGLETON = Collections.unmodifiableList(Arrays.asList(
            Arena.class,
            Block.class,
            CancelMessage.class,
            ControlsMessage.class,
            Effect.class,
            EffectType.class,
            GameMode.class,
            GameOverMessage.class,
            GameOverMessagePlayer.class,
            IntermediaryEndMessage.class,
            IntermediaryStartMessage.class,
            IntermediaryStartMessagePlayer.class,
            MatchmadeMessage.class,
            MatchmadeMessagePlayer.class,
            PlayMessage.class,
            Player.class,
            PlayerSkin.class,
            Position.class,
            Powerup.class,
            PowerupType.class,
            Projectile.class,
            ProjectileType.class,
            SoundEffect.class,
            UpdateMessage.class,
            UpdateMessagePlayer.class
        ));
    }

    /**
     * All message classes that Kryo should register.
     * @return the list of all message classes.
     */
    public static List<Class> getMessageClasses() {
        return MessageClassListHolder.CLASSES_SINGLETON;
    }

}
