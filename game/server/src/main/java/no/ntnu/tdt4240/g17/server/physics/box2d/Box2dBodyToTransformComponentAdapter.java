package no.ntnu.tdt4240.g17.server.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Adapts box2d {@link Body} to Ashley {@link TransformComponent}.
 *
 * Can both create and adapt.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class Box2dBodyToTransformComponentAdapter {

    /**
     * Create a new {@link TransformComponent} from a {@link Body}.
     * @param body the body to copy state from
     * @return a new TransformComponent.
     */
    public TransformComponent createFrom(final Body body) {
        return new TransformComponent(new Vector2(body.getPosition()), new Vector2(1f, 1f), body.getAngle());
    }

    /**
     * Copies over data from a body to a transform component.
     * @param body the body to copy data from
     * @param transformComponent the component to write into
     */
    public void adapt(final Body body, final TransformComponent transformComponent) {
        transformComponent.setPosition(new Vector2(body.getPosition()));
        transformComponent.setRotation(body.getAngle());
    }
}
