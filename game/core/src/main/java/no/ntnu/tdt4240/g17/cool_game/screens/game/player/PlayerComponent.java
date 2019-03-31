package no.ntnu.tdt4240.g17.cool_game.screens.game.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.ntnu.tdt4240.g17.cool_game.character.GameCharacter;
/**
 * Component for players.
 * @author Håvard 'havfar' Farestveit
 */
public class PlayerComponent implements Component {

    private GameCharacter character;
    private int playerId;

    /**
     * Constructor.
     * @param id = Unique id for player
     * @param xPosistion = x position in map
     * @param yPosistion = y position in map
     * @param name = texture name
     * @param atlas = texture atalas
     */
    public PlayerComponent(
            final int id,
            final float xPosistion,
            final float yPosistion,
            final String name,
            final TextureAtlas atlas) {
        playerId = id;
        character = new GameCharacter(name, xPosistion, yPosistion, atlas);
    }

    /**
     * @return the id to this player
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * @return the Game character class tot this player
     */
    public GameCharacter getCharacter() {
        return character;
    }
}


