package cz.hatoff.footmen.client;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import cz.hatoff.footmen.camera.RtsCameraInitializer;
import cz.hatoff.footmen.client.state.LoadPlayerAppState;
import cz.hatoff.footmen.commons.MouseCoordinates;
import cz.hatoff.footmen.control.MoveControl;
import cz.hatoff.footmen.network.FootmenNetwork;
import cz.hatoff.footmen.network.MessageSerializer;
import cz.hatoff.footmen.network.mesage.move.MoveRequest;
import cz.hatoff.footmen.network.mesage.move.MoveResponse;
import cz.hatoff.footmen.scene.CharacterLoader;
import cz.hatoff.footmen.scene.SceneLoader;
import cz.hatoff.footmen.client.state.SelectAppState;
import cz.hatoff.footmen.network.mesage.player.AddNewPlayer;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FootmenClientMain extends SimpleApplication {

    private Client client;
    
    private Node scene;
    private SceneLoader sceneLoader;
    

    public static void main(String[] args) {
        MessageSerializer.initializeSerializables();
        FootmenClientMain app = new FootmenClientMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        this.pauseOnFocus = false;
        
        createClient();
        
        sceneLoader = new SceneLoader(assetManager);
        
        SelectAppState selectAppState = new SelectAppState();
        stateManager.attach(selectAppState);

        scene = sceneLoader.loadScene();
        rootNode.attachChild(scene);

        RtsCameraInitializer.initializeRtsCamera(stateManager);

        Node sinbad1 = new CharacterLoader(assetManager).createSinbad("1");
        Node sinbad2 = new CharacterLoader(assetManager).createSinbad("2");
        
        selectAppState.addSelectable(sinbad1);
        selectAppState.addSelectable(sinbad2);
        
        rootNode.attachChild(sinbad1);
        rootNode.attachChild(sinbad2);
        
        LoadPlayerAppState loadPlayerAppState = new LoadPlayerAppState(sceneLoader);        
        stateManager.attach(loadPlayerAppState);
        client.addMessageListener(loadPlayerAppState, AddNewPlayer.class);
        
        createLights();        

        inputManager.addMapping("moveOrAttack", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                final Spatial selected = stateManager.getState(SelectAppState.class).getSelected();
                if (isPressed && selected != null) {
                    final MouseCoordinates coords = getMouseCoordinates();
                    client.send(new MoveRequest(selected.getName(), coords.getPosition(), coords.getDirection()));
                } 
            }
        }, "moveOrAttack");

        

    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void createClient() {
        try {
            client = Network.connectToServer("127.0.0.1", FootmenNetwork.PORT);    
            client.start();
            client.addMessageListener(new MoveResponseListener(), MoveResponse.class);
        } catch (IOException ex) {
            Logger.getLogger(FootmenClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {
        client.close();
        super.destroy();
    }

    private void createLights() {
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        rootNode.addLight(sun);
    }

    private class MoveResponseListener implements MessageListener<Client> {
        public void messageReceived(Client source, Message m) {
            final MoveResponse moveResponse = (MoveResponse) m;
            final Spatial spatial = rootNode.getChild(moveResponse.getId());
            if(spatial != null) {
                FootmenClientMain.this.enqueue(new Callable() {
                    public Object call() throws Exception {
                        final MoveControl control = spatial.getControl(MoveControl.class);
                        control.setPosition(moveResponse.getVector());
                        return null;
                    }
                });
            }

        }
    }

    private MouseCoordinates getMouseCoordinates() {
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f direction = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        return new MouseCoordinates(click3d, direction);
    }
}
