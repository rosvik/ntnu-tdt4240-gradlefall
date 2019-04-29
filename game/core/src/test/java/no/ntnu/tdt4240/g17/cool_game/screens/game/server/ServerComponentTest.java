package no.ntnu.tdt4240.g17.cool_game.screens.game.server;

import no.ntnu.tdt4240.g17.cool_game.screens.game.controller.ControllerServerComponent;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


class ServerComponentTest {
    ControllerServerComponent controller = new ControllerServerComponent();
    ServerComponent server = new ServerComponent(null, controller);

    @Test
    void fetchFromAPI() {
        server.fetchFromAPI();
    }

    @Test
    void getX() {
        Assert.assertThat(server.getX(), Matchers.equalTo(0f));
    }

    @Test
    void getY() {
        Assert.assertThat(server.getY(), Matchers.equalTo(0f));
    }

    @Test
    void shootPressed() {
        Assert.assertFalse(server.shootPressed());
        server.shoot = true;
        Assert.assertTrue(server.shootPressed());
    }

    @Test
    void getAngle() {
        Assert.assertThat(server.getAngle(), Matchers.equalTo(0f));
    }
}