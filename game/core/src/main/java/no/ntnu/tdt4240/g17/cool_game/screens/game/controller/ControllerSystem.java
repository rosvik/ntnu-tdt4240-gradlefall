package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.Engine;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;
import no.ntnu.tdt4240.g17.cool_game.network.GameClient;

import java.util.Collections;

/**
 * System that sends data from controllerComponent to ControllerServerComponent.
 * @author Håvard 'havfar' Farestveit
 */
public class ControllerSystem extends EntitySystem {
    private Family family;
    private ComponentMapper<ControllerComponent> controller;
    private ComponentMapper<ControllerServerComponent> controllerServer;
    private Iterable<Entity> entitiesToUpdate = Collections.emptyList();
    private GameClient gameClient;
    private ControlsMessage controlMessage;

    /**
     * Constructor.
     * Family = all entities with a controller and server connection
     */
    public ControllerSystem() {
        family = Family.all(ControllerComponent.class, ControllerServerComponent.class).get();
        controller = ComponentMapper.getFor(ControllerComponent.class);
        controllerServer = ComponentMapper.getFor(ControllerServerComponent.class);
        controlMessage = new ControlsMessage();
    }

    /**
     * Sends data from controller to server.
     * @param deltaTime
     */
    @Override
    public void update(final float deltaTime) {
        for (Entity entity : entitiesToUpdate) {
            ControllerComponent entityController = controller.get(entity);
            //ControllerServerComponent entityServer = controllerServer.get(entity);
            entityController.update();
            //entityServer.update(entityController.getMovementFormat());
        }
    }


    @Override
    public final void addedToEngine(final Engine engine) {
        entitiesToUpdate = engine.getEntitiesFor(family);
    }

    @Override
    public final void removedFromEngine(final Engine engine) {
        entitiesToUpdate = Collections.emptyList();
    }
}
