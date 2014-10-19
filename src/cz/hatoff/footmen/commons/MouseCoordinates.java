
package cz.hatoff.footmen.commons;

import com.jme3.math.Vector3f;


public class MouseCoordinates {
    
    private Vector3f position;
    private Vector3f direction;

    public MouseCoordinates(Vector3f position, Vector3f direction) {
        this.position = position;
        this.direction = direction;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
    
}
