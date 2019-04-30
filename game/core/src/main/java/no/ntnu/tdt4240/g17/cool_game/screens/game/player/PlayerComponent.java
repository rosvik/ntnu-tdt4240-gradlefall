package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import lombok.Getter;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Position;
import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
/**
 * Component for players.
 * @author Håvard 'havfar' Farestveit
 */
@Getter
public class PlayerComponent implements Component {
    /** Gets PlayerComponent from entities. */
    public static final ComponentMapper<PlayerComponent> MAPPER = ComponentMapper.getFor(PlayerComponent.class);

    private GameCharacter character;
    private String playerId;

    /**
     * Constructor.
     * @param id = Unique id for player
     * @param position = object posistion
     * @param name = texture name
     * @param atlas = texture atalas
     */
    public PlayerComponent(
            final String id,
            final Position position,
            final String name,
            final TextureAtlas atlas) {
        playerId = id;
        character = new GameCharacter(name, position.x, position.y, atlas);
    }
}


