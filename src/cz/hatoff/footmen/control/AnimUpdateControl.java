/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.hatoff.footmen.control;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import cz.hatoff.footmen.commons.Constans;
import java.io.IOException;

/**
 *
 * @author normenhansen
 */
public class AnimUpdateControl extends AbstractControl {

    public static final String RUN_TOP = "RunTop";
    public static final String RUN_BASE = "RunBase";
    public static final String IDLE_TOP = "IdleTop";
    public static final String IDLE_BASE = "IdleBase";
    
    private AnimControl animControl;
    private AnimChannel torsoChannel;
    private AnimChannel feetChannel;

    @Override
    protected void controlUpdate(float tpf) {

        if (checkAnimControl()) {
            if (spatial.getParent().getUserData(Constans.STATE) == cz.hatoff.footmen.commons.State.RUNNING.getStateNumber()) {
                if (!RUN_TOP.equals(torsoChannel.getAnimationName())) {
                    torsoChannel.setAnim(RUN_TOP);
                }
                if (!RUN_BASE.equals(feetChannel.getAnimationName())) {
                    feetChannel.setAnim(RUN_BASE);
                }
            } else {
                if (!IDLE_TOP.equals(torsoChannel.getAnimationName())) {
                    torsoChannel.setAnim(IDLE_TOP);
                }
                if (!IDLE_BASE.equals(feetChannel.getAnimationName())) {
                    feetChannel.setAnim(IDLE_BASE);
                }
            }
        }
    }

    /**
     * Checks if the animcontrol is available and creates channels if not done
     * already.
     */
    private boolean checkAnimControl() {
        AnimControl control = spatial.getControl(AnimControl.class);
        if (control != animControl) {
            this.animControl = control;
            if (animControl != null) {
                torsoChannel = animControl.createChannel();
                feetChannel = animControl.createChannel();
            }
        }
        return animControl != null;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        AnimUpdateControl control = new AnimUpdateControl();
        //TODO: copy parameters to new Control
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        //TODO: load properties of this Control, e.g.
        //this.value = in.readFloat("name", defaultValue);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        //TODO: save properties of this Control, e.g.
        //out.write(this.value, "name", defaultValue);
    }
}
