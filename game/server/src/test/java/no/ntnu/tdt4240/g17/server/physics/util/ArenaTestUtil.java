package no.ntnu.tdt4240.g17.server.physics.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.server.game_engine.player.PlayerComponent;
import no.ntnu.tdt4240.g17.server.game_engine.projectile.ProjectileComponent;
import no.ntnu.tdt4240.g17.server.physics.ArenaSimulationIT;
import no.ntnu.tdt4240.g17.server.physics.box2d.Box2dBodyComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.TransformComponent;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.ArenaTileBox2dBodyFactory;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.CharacterBox2dBodyFactory;
import no.ntnu.tdt4240.g17.server.physics.box2d.body.ProjectileBox2dBodyFactory;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/19/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class ArenaTestUtil {
    private ArenaTestUtil() { }


    /**
     * Create an arena in box2d with tiles and characters from the given arena string.
     *
     * <br/><br/>
     * #sokobak #tretteberg
     * @param arena a string with <code>#</code>, <code>"space"</code> and <code>0-9</code>
     * @param world
     * @return
     */
    public static List<Entity> createComponentsFromArena(final String arena, World world) {
        String[] rows = arena.split("\n");
        int rowCount = rows.length;
        int columnCount = rows[0].length();

        log.debug("Creating world of size ({}, {})", rowCount, columnCount);

        final CharacterBox2dBodyFactory characterFactory = new CharacterBox2dBodyFactory(world, 1f);
        final ArenaTileBox2dBodyFactory arenaFactory = new ArenaTileBox2dBodyFactory(world, 1f, 1f);
        final ProjectileBox2dBodyFactory projectileFactor = new ProjectileBox2dBodyFactory(world);
        final int entityCount = arena.replaceAll(" ", "").length();

        List<Entity> entities = new ArrayList<>(entityCount);

        for (int r = 0; r < rows.length; r++) {
            String row = rows[r];
            final char[] chars = row.toCharArray();
            for (int c = 0; c < chars.length; c++) {
                char letter = chars[c];
                if (letter == ' ') {
                    continue;
                }
                final Entity entity = new Entity();
                entities.add(entity);

                // x and y are in meters
                final int x = c;
                final int y = rowCount - r - 1;
                ;

                if (Character.isDigit(letter)) {
                    int playerNumber = Character.digit(letter, 10);
                    log.trace("Creating player {}", playerNumber);
                    final Body body = characterFactory.create(entity);
                    body.setTransform(x, y, 0f);
                    entity.add(new Box2dBodyComponent(body));
                    entity.add(new TransformComponent(new Vector2(body.getPosition()), new Vector2(1f, 1f), body.getAngle()));
                    entity.add(new PlayerComponent("" + playerNumber, "Player " + playerNumber));
                } else if (letter == '#') {
                    final Body body = arenaFactory.create(entity);
                    body.setTransform(x, y, 0f);
                    entity.add(new Box2dBodyComponent(body));
                } else if (letter == '-') {
                    final Body body = projectileFactor.create(entity);
                    body.setTransform(x, y, 0f);
                    entity.add(new Box2dBodyComponent(body));
                    entity.add(new TransformComponent(new Vector2(body.getPosition()), new Vector2(1f, 1f), body.getAngle()));
                    entity.add(new ProjectileComponent(null));
                }
            }
        }

        return entities;
    }
}
