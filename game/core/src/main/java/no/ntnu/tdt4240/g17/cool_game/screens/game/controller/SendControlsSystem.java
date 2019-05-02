package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.badlogic.ashley.systems.IntervalSystem;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;

/**
 * Sends controls at an interval.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public final class SendControlsSystem extends IntervalSystem {
    private Consumer<ControlsMessage> controlsMessageNetworkSender;

    /**
     * @param interval how often in seconds
     * @param controlsMessageNetworkSender will get the message to send over network
     */
    public SendControlsSystem(final float interval, final Consumer<ControlsMessage> controlsMessageNetworkSender) {
        super(interval);
        this.controlsMessageNetworkSender = controlsMessageNetworkSender;
    }

    /**
     * @param interval how often in seconds
     * @param priority priority in ecs engine. Lower comes first.
     * @param controlsMessageNetworkSender will get the message to send over network
     */
    public SendControlsSystem(final float interval, final int priority,
                              final Consumer<ControlsMessage> controlsMessageNetworkSender) {
        super(interval, priority);
        this.controlsMessageNetworkSender = controlsMessageNetworkSender;
    }

    @Override
    protected void updateInterval() {
        final ControlsMessage controlsMessage = GUI.getInstance().update();
        log.trace("Sending controls {}", controlsMessage);
        controlsMessageNetworkSender.accept(controlsMessage);
    }
}
