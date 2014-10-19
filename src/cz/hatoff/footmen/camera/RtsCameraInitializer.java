
package cz.hatoff.footmen.camera;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import cz.hatoff.footmen.camera.RtsCam.DoF;
import cz.hatoff.footmen.camera.RtsCam.HeightProvider;
import cz.hatoff.footmen.camera.RtsCam.UpVector;


public class RtsCameraInitializer {
    
    private static int MIN_HEIGHT = 20;
    
    public static void initializeRtsCamera(AppStateManager stateManager) {
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        RtsCam rtsCam = new RtsCam(UpVector.Y_UP);
        rtsCam.setCenter(new Vector3f(0, 0, 0));
        rtsCam.setDistance(200);
        rtsCam.setMaxSpeed(DoF.FWD, 100, 0.5f);
        rtsCam.setMaxSpeed(DoF.SIDE, 100, 0.5f);
        rtsCam.setMaxSpeed(DoF.DISTANCE, 100, 0.5f);
        rtsCam.setHeightProvider(new HeightProvider() {
            public float getHeight(Vector2f coord) {
                 return MIN_HEIGHT;
             }
         });
        stateManager.attach(rtsCam);
    }
    
}
