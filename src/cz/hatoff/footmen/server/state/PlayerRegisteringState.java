package cz.hatoff.footmen.server.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.sun.istack.internal.logging.Logger;
import cz.hatoff.footmen.commons.Constans;
import cz.hatoff.footmen.model.Player;
import cz.hatoff.footmen.model.Slot;
import cz.hatoff.footmen.network.mesage.player.AddNewPlayer;
import cz.hatoff.footmen.network.mesage.slot.SlotAssignment;
import cz.hatoff.footmen.scene.SceneLoader;
import java.util.EnumMap;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class PlayerRegisteringState extends AbstractAppState implements ConnectionListener {
    
    private static final Logger logger = Logger.getLogger(PlayerRegisteringState.class);

    private EnumMap<Slot, Player> players;
    private Server server;
    private Application app;
    private SceneLoader sceneLoader;

    public PlayerRegisteringState(Server server, SceneLoader sceneLoader) {
        this.players = new EnumMap<Slot, Player>(Slot.class);
        this.server = server;
        this.sceneLoader = sceneLoader;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        server.addConnectionListener(this);
        this.app = app;
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        final Slot slot = getNextFreeSlot();
        logger.log(Level.INFO, "Registering new player into " + slot);
        if (slot != null) {
            conn.setAttribute(Constans.SLOT, slot);
          //  conn.send(new SlotAssignment(slot.getSlotId()));
            conn.send(new AddNewPlayer(slot.getSlotId()));
            players.put(slot, new Player());
            buildPlayerBase(slot);
            server.broadcast(new AddNewPlayer(slot.getSlotId()));
        }
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        players.remove((Slot) conn.getAttribute(Constans.SLOT));
    }

    public synchronized Slot getNextFreeSlot() {
        for (Slot slot : Slot.values()) {
            if (!players.containsKey(slot)) {
                return slot;
            }
        }
        return null;
    }

    private void buildPlayerBase(final Slot slot) {
        app.enqueue(new Callable<Object>() {
            public Object call() throws Exception {
                sceneLoader.loadPlayerBase(slot);
                return null;
            }
        });      
    }
}
