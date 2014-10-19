package cz.hatoff.footmen.server;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.JmeContext;
import cz.hatoff.footmen.control.MoveControl;
import cz.hatoff.footmen.network.FootmenNetwork;
import cz.hatoff.footmen.network.MessageSerializer;
import cz.hatoff.footmen.network.mesage.move.MoveRequest;
import cz.hatoff.footmen.network.mesage.move.MoveResponse;
import cz.hatoff.footmen.scene.CharacterLoader;
import cz.hatoff.footmen.scene.SceneLoader;
import cz.hatoff.footmen.server.state.PlayerRegisteringState;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FootmenServerMain extends SimpleApplication {

    private Server server;
    private Node scene;
    
    private SceneLoader sceneLoader;
    
    
    public static void main(String[] args) {
        MessageSerializer.initializeSerializables();
        FootmenServerMain app = new FootmenServerMain();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        createServer();

        sceneLoader = new SceneLoader(assetManager);
        scene = sceneLoader.loadScene();
        rootNode.attachChild(scene);

        Node sinbad1 = new CharacterLoader(assetManager).createSinbad("1");
        Node sinbad2 = new CharacterLoader(assetManager).createSinbad("2");

        rootNode.attachChild(sinbad1);
        rootNode.attachChild(sinbad2);
        
        stateManager.attach(new PlayerRegisteringState(server, sceneLoader));
        

    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void createServer() {
        try {
            server = Network.createServer(FootmenNetwork.PORT);
            server.start();
            server.addMessageListener(new MoveRequestListener(), MoveRequest.class);
            Logger.getLogger(FootmenServerMain.class.getName()).log(Level.INFO, "Server has been started at port {0}", FootmenNetwork.PORT);
        } catch (IOException ex) {
            Logger.getLogger(FootmenServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {
        server.close();
        super.destroy();
    }

    private class MoveRequestListener implements MessageListener<HostedConnection> {

        public void messageReceived(HostedConnection source, Message m) {
         
            final MoveRequest moveRequest = (MoveRequest) m;
            final Spatial spatial = rootNode.getChild(moveRequest.getId());
            if (spatial != null) {

                Ray ray = new Ray(moveRequest.getClick(), moveRequest.getDirection());
                CollisionResults collisionResults = new CollisionResults();
                scene.collideWith(ray, collisionResults);

                if (collisionResults.size() > 0) {
                    final CollisionResult collision = collisionResults.getClosestCollision();
                    final Vector3f contactPoint = collision.getContactPoint();
                    final Vector3f newPosition = new Vector3f(contactPoint.x, contactPoint.y + 5, contactPoint.z);
                    FootmenServerMain.this.enqueue(new Callable() {
                        public Object call() throws Exception {
                            final MoveControl control = spatial.getControl(MoveControl.class);
                            control.setPosition(newPosition);
                            server.broadcast(new MoveResponse(spatial.getName(), newPosition));
                            return null;
                        }
                    });
                }
            }

        }
    }
}
