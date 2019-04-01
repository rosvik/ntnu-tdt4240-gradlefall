package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.libgdx.test.util.GameTest;
import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerComponentTest extends GameTest {
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./TextureAtlas/Characters/DungeonTileset.atlas"));
    PlayerComponent playerComponent = new PlayerComponent(0,0,0,"knight_m", atlas);
    @Test
    void getPlayerId() {
        Assert.assertThat(0, Matchers.equalTo(playerComponent.getPlayerId()));
    }

    @Test
    void getCharacter() {
        Assert.assertThat(playerComponent.getCharacter(), Matchers.isA(GameCharacter.class));
    }
}