package no.ntnu.tdt4240.g17.server.physics.box2d;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import lombok.Setter;

/**
 * A component for transoform data for use in physics.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Getter @Setter
public class TransformComponent implements Component {
    /** Mapper to get the component from an entity. */
    public static final ComponentMapper<TransformComponent> MAPPER = ComponentMapper.getFor(TransformComponent.class);

    private Vector2 position;
    private Vector2 scale;
    /** Depth into screen (z axis). 0 is the screen, negative is deeper into the screen. */
    private int depth;
    /** Rotation in radians. */
    private float rotation;

    /** Create a new component.
     * @param position initial position
     * @param scale initial scale
     * @param rotation initial rotation (radians)
     */
    public TransformComponent(final Vector2 position, final Vector2 scale, final float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }
}
