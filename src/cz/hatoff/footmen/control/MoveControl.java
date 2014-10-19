/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.hatoff.footmen.control;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import cz.hatoff.footmen.commons.Constans;
import cz.hatoff.footmen.commons.State;


public class MoveControl extends AbstractControl{
    
    private Vector3f position;
    private float speed = 10.0f;
    private final Quaternion lookRotation = new Quaternion();

    public MoveControl() {
    }
    
    public MoveControl(Vector3f position) {
        this.position = position;
    }
  
    @Override
    protected void controlUpdate(float tpf) {
        if (position != null) {
            Vector3f dist = position.subtract(spatial.getWorldTranslation());
            if (dist.length() < 1) {
                position = null;
                spatial.setUserData(Constans.STATE, State.IDLE.getStateNumber());
            } else {
                dist.normalizeLocal();
                lookRotation.lookAt(dist, Vector3f.UNIT_Y);
                spatial.setLocalRotation(lookRotation);
                spatial.move(dist.multLocal(speed * tpf));
                spatial.setUserData(Constans.STATE, State.RUNNING.getStateNumber());
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    
}
