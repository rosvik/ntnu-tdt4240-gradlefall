package no.ntnu.tdt4240.g17.server.game_engine.projectile;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/18/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class ProjectileComponentTest {


    @Test
    void constructorShouldAcceptNull() {
        // When
        new ProjectileComponent(null);
        // Then

        // no exceptions
    }
}