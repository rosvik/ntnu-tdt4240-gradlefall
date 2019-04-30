package no.ntnu.tdt4240.g17.cool_game.screens.game.controller;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.ControlsMessage;

/**
 * System that sends data from controllerComponent to ControllerServerComponent.
 * @author Håvard 'havfar' Farestveit
 */
@Slf4j
public class ControllerSystem extends EntitySystem {
    /** Component family for controller entities. */
    public static final Family FAMILY_CONTROLLER_SYSTEM = Family.all(
            ControllerComponent.class,
            ControllerServerComponent.class).get();

    private Family family;
    private ComponentMapper<ControllerComponent> controller;
    private ComponentMapper<ControllerServerComponent> controllerServer;
    private Iterable<Entity> entitiesToUpdate = Collections.emptyList();
    @Setter @Nullable
    private ServerConnection serverConnection;

    /** Bridges ECS and client-server with loose coupling. */
    @FunctionalInterface
    public interface ServerConnection {
        /**
         * @param message the message to send to the server.
         */
        void send(ControlsMessage message);
    }

    /**
     * Constructor.
     * Family = all entities with a controller and server connection
     * @see #FAMILY_CONTROLLER_SYSTEM
     */
    public ControllerSystem() {
        family = FAMILY_CONTROLLER_SYSTEM;
        controller = ComponentMapper.getFor(ControllerComponent.class);
        controllerServer = ComponentMapper.getFor(ControllerServerComponent.class);
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
            final ControlsMessage message = entityController.getMessage();
            if (serverConnection != null) {
                serverConnection.send(message);
            } else {
                log.debug("No server connection set in ControllerSystem '{}'!", this);
            }
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
