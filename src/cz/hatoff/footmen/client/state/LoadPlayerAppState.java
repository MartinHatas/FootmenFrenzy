/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.hatoff.footmen.client.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.sun.istack.internal.logging.Logger;
import cz.hatoff.footmen.model.Slot;
import cz.hatoff.footmen.network.mesage.player.AddNewPlayer;
import cz.hatoff.footmen.scene.SceneLoader;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class LoadPlayerAppState extends AbstractAppState implements MessageListener<Client> {

    private static final Logger logger = Logger.getLogger(LoadPlayerAppState.class);
    
    private Application app;
    private SceneLoader sceneLoader;

    public LoadPlayerAppState(SceneLoader sceneLoader) {
        logger.log(Level.CONFIG, "Creating player loading app state component.");
        this.sceneLoader = sceneLoader;    
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        logger.log(Level.CONFIG, "Initializing player loading app state component.");
        super.initialize(stateManager, app);
        this.app = app;
    }

    public void messageReceived(Client source, Message m) {  
        final AddNewPlayer addNewPlayer = (AddNewPlayer) m;
        final Slot slot = Slot.getSlotById(addNewPlayer.getSlotId());
        logger.log(Level.INFO, "Received Add new player command. " + slot);
        app.enqueue(new Callable() {
            public Object call() throws Exception {
                sceneLoader.loadPlayerBase(slot);
                return null;
            }
        });

    }
}
