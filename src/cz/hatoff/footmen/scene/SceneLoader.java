
package cz.hatoff.footmen.scene;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import cz.hatoff.footmen.model.Slot;



public class SceneLoader {
    
    private AssetManager assetManager;
    private Box baseShape;
       
    public static final String SCENE_FILE = "Scenes/map.j3o";
    public static final String BASE_MATERIAL = "Common/MatDefs/Misc/Unshaded.j3md";
    
    private Material UNSHADED_MATERIAL;
    private Node sceneNode;
    
    public SceneLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
        UNSHADED_MATERIAL = new Material(assetManager, BASE_MATERIAL);
        baseShape = new Box(20, 20, 20);
    }
      

    public Node loadScene() {  
        sceneNode = new Node();
        
        Spatial terrain = assetManager.loadModel(SCENE_FILE);
        sceneNode.attachChild(terrain);
        
        Box wall = new Box(32, 3, 32);
        
        Material material = new Material(assetManager, BASE_MATERIAL);
        material.setColor("Color", ColorRGBA.DarkGray);
        
        Geometry westWallGeom = new Geometry("westWall", wall);
        westWallGeom.setMaterial(material);
        westWallGeom.setLocalTranslation(96f, 5, 0);
        sceneNode.attachChild(westWallGeom);
        
        Geometry eastWallGeom = new Geometry("eastWall", wall);
        eastWallGeom.setMaterial(material);
        eastWallGeom.setLocalTranslation(-96f, 5, 0);
        sceneNode.attachChild(eastWallGeom);
        
        Geometry nortWallGeom = new Geometry("nortWallGeom", wall);
        nortWallGeom.setMaterial(material);
        nortWallGeom.setLocalTranslation(0, 5, 96f);
        sceneNode.attachChild(nortWallGeom);
        
        Geometry southWallGeom = new Geometry("southWallGeom", wall);
        southWallGeom.setMaterial(material);
        southWallGeom.setLocalTranslation(0, 5, -96f);
        sceneNode.attachChild(southWallGeom);
        
        sceneNode.addControl(new RigidBodyControl(0));
        return sceneNode;
    }
    
    public void loadPlayerBase(Slot slot) {
        Spatial baseSpatial = new Geometry("box", baseShape); 
        Material material = UNSHADED_MATERIAL.clone();
        material.setColor("Color", slot.getColor());
        baseSpatial.setMaterial(UNSHADED_MATERIAL);
        baseSpatial.setLocalTranslation(slot.getBaseVector());
        sceneNode.attachChild(baseSpatial);
    }
  
}
