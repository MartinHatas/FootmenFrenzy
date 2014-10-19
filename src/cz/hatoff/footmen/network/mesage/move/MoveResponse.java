
package cz.hatoff.footmen.network.mesage.move;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class MoveResponse extends AbstractMessage{
    
    private String id;
    private Vector3f vector;

    public MoveResponse() {
    }

    public MoveResponse(String id, Vector3f vector) {
        this.id = id;
        this.vector = vector;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vector3f getVector() {
        return vector;
    }

    public void setVector(Vector3f vector) {
        this.vector = vector;
    }
    
    
    
}
