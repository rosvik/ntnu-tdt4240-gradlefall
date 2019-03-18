package no.ntnu.tdt4240.g17.server.physics.box2d;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Rectangle;

/**
 * Holds a bounding box to easily find the size of an entity.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class BoundingBoxComponent implements Component {

    /** The mapper to get this component from {@link com.badlogic.ashley.core.Entity entities}. */
    public static final ComponentMapper<BoundingBoxComponent> MAPPER =
            ComponentMapper.getFor(BoundingBoxComponent.class);

    private Rectangle boundingBox;

    /**
     * Create a new component.
     * @param boundingBox the bounding box
     */
    public BoundingBoxComponent(final Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }
}
