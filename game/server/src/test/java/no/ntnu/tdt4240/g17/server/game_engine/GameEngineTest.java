package no.ntnu.tdt4240.g17.server.game_engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerEntityFactory;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.CharacterBox2dBodyFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Kristian 'krissrex' Rekstad on 4/30/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
class GameEngineTest {

    private Engine ecsEngine;
    private GameEngine gameEngine;
    private PlayerEntityFactory playerEntityFactory;
    private World world;

    @BeforeAll
    static void init() {
        Box2D.init();
    }

    @BeforeEach
    void setUp() {
        ecsEngine = new Engine();
        world = new World(new Vector2(0f, -9.81f), true);
        playerEntityFactory = new PlayerEntityFactory(new CharacterBox2dBodyFactory(world, 1f));
    }

    @AfterEach
    void tearDown() {
        world.dispose();
    }

    @Test
    void shouldBeOverWhenOneOfOnePlayerRemain() {
        // Given
        gameEngine = new GameEngine(ecsEngine);
        final Entity entity = playerEntityFactory.create("1", "Player 1");
        ecsEngine.addEntity(entity);

        // When
        final boolean gameOver = gameEngine.doGameOverCheck();

        // Then
        assertThat(gameOver, is(true));
    }

    @Test
    void shouldNotBeOverWhenTwoOfTwoPlayersRemain() {
        // Given
        final GameEngine gameEngine = new GameEngine(ecsEngine);
        final Entity entity = playerEntityFactory.create("1", "Player 1");
        final Entity entity2 = playerEntityFactory.create("2", "Player 2");
        ecsEngine.addEntity(entity);
        ecsEngine.addEntity(entity2);

        // When
        final boolean gameOver = gameEngine.doGameOverCheck();

        // Then
        assertThat(gameOver, is(false));
    }

    @Test
    void shouldBeOverWhenOneOfTwoPlayersRemainAndOtherPlayerIsDead() {
        // Given
        final GameEngine gameEngine = new GameEngine(ecsEngine);
        final Entity entity = playerEntityFactory.create("1", "Player 1");
        final Entity entity2 = playerEntityFactory.create("2", "Player 2");
        ecsEngine.addEntity(entity);
        ecsEngine.addEntity(entity2);

        // When
        PlayerComponent.MAPPER.get(entity2).setAlive(false);
        final boolean gameOver = gameEngine.doGameOverCheck();

        // Then
        assertThat(gameOver, is(true));
    }

    @Test
    void shouldBeOverWhenAllPlayersAreDead() {
        // Given
        final GameEngine gameEngine = new GameEngine(ecsEngine);
        final Entity entity = playerEntityFactory.create("1", "Player 1");
        final Entity entity2 = playerEntityFactory.create("2", "Player 2");
        ecsEngine.addEntity(entity);
        ecsEngine.addEntity(entity2);

        PlayerComponent.MAPPER.get(entity).setAlive(false);
        PlayerComponent.MAPPER.get(entity2).setAlive(false);

        // When
        final boolean gameOver = gameEngine.doGameOverCheck();

        // Then
        assertThat(gameOver, is(true));
    }
}