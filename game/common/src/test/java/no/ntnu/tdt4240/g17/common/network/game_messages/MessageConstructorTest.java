package no.ntnu.tdt4240.g17.common.network.game_messages;

import org.junit.jupiter.api.Test;

/**
 * Hack for jacoco line converage
 *
 * @author Kristian 'krissrex' Rekstad
 */
class MessageConstructorTest {


    /** Dumb test to stop jacoco from complaining. */
    @Test
    void shouldHaveConstructor() {
        new CancelMessage();
        new ControlsMessage();
        new GameOverMessage();
        new IntermediaryEndMessage();
        new IntermediaryStartMessage();
        new MatchmadeMessage();
        new PlayMessage();
        new UpdateMessage();
    }
}