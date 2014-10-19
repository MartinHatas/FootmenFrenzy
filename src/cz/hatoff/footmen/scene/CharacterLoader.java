
package cz.hatoff.footmen.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import cz.hatoff.footmen.commons.Constans;
import cz.hatoff.footmen.commons.State;
import cz.hatoff.footmen.control.AnimUpdateControl;
import cz.hatoff.footmen.control.MoveControl;
import cz.hatoff.footmen.control.SelectableControl;


public class CharacterLoader {
    
    private Box boxShape;
    private Material unshadedMaterial;

    private AssetManager assetManager;
    
    public CharacterLoader(AssetManager assetManager) { 
        this.assetManager = assetManager;
        boxShape = new Box(2, 2, 2);
        unshadedMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
        unshadedMaterial.setColor("Color", ColorRGBA.Blue);
        
    }
    
    public Spatial createBox(){
        Spatial boxSpatial = new Geometry("box", boxShape);
        boxSpatial.setMaterial(unshadedMaterial);
        boxSpatial.setLocalTranslation(0, 2, 0);
        boxSpatial.addControl(new MoveControl());
        return boxSpatial;
    } 
    

    public Node createSinbad(String name) {
        Spatial sinbad = assetManager.loadModel("Models/Sinbad.j3o");
        sinbad.addControl(new AnimUpdateControl());  
        
        Node sinbadNode = new Node(name);
        sinbadNode.attachChild(sinbad);
        
        sinbadNode.addControl(new MoveControl());
        
        Box box = new Box(0.5f, 0.5f, 0.5f);
        Geometry marker = new Geometry("marker", box);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Green);
        marker.setMaterial(material);
        marker.setLocalTranslation(0, 6, 0);
        sinbadNode.addControl(new SelectableControl(marker));
        
        sinbadNode.setUserData(Constans.STATE, State.IDLE.getStateNumber());
        sinbadNode.setLocalTranslation(0, 5, 0);
      
        return sinbadNode;    
    }
    
    
    
}
