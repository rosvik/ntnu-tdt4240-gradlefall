package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;

import com.libgdx.test.util.GameTest;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
class GUITest extends GameTest {
    GUI gui;

    @BeforeEach
    void setUp() {
        gui = GUI.getInstance();
    }

    @Test
    void getInstance() {
        Assert.assertThat(gui, is(notNullValue()));
    }

    @Test
    void update() {
        // Given
        log.debug("Movement format: {}", gui.getMovementFormat());

        // When
        final ControlsMessage controlsMessage = gui.update();

        // Then
        float angle = controlsMessage.moveAngle;
        float magnitude = controlsMessage.moveSpeed;
        boolean jump = controlsMessage.jump;
        boolean shoot = controlsMessage.shoot;
        boolean block = controlsMessage.placeBlock;
        assertThat(angle, is(0f));
        assertThat(magnitude, is(0f));
        assertThat(jump, is(false));
        assertThat(shoot, is(false));
        assertThat(block, is(false));
    }
}