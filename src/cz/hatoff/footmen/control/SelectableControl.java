package cz.hatoff.footmen.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class SelectableControl extends AbstractControl {

    private boolean selected = false;
    private Spatial marker;

    public SelectableControl(Spatial marker) {
        this.marker = marker;
    }    
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        if (marker != null) {
            if (this.selected) {      
                ((Node) spatial).attachChild(marker);
            } else {
                ((Node) spatial).detachChild(marker);
            }
        }
    }

    public void setMarker(Spatial marker) {
        this.marker = marker;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
