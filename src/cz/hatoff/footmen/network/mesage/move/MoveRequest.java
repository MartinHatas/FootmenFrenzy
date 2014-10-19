/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.hatoff.footmen.network.mesage.move;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class MoveRequest extends AbstractMessage{
    
    private String id;
    private Vector3f click;
    private Vector3f direction;

    public MoveRequest() {
    }

    public MoveRequest(String id, Vector3f click, Vector3f direction) {
        this.id = id;
        this.click = click;
        this.direction = direction;
    }

    public Vector3f getClick() {
        return click;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
 
}
