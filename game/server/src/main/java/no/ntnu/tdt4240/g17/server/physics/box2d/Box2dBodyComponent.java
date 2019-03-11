package no.ntnu.tdt4240.g17.server.physics.box2d;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/11/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class Box2dBodyComponent implements Component {
    /** Box2d body. */
    private Body body;

    /** Create a new component for box2d.
     * @param body the box2d body.
     */
    public Box2dBodyComponent(final Body body) {
        this.body = body;
    }

    /** @return Box2d body. */
    public Body getBody() {
        return body;
    }
}
