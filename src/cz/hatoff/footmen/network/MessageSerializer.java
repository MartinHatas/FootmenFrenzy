package cz.hatoff.footmen.network;

import com.jme3.network.serializing.Serializer;
import cz.hatoff.footmen.network.mesage.move.MoveRequest;
import cz.hatoff.footmen.network.mesage.move.MoveResponse;
import cz.hatoff.footmen.network.mesage.player.AddNewPlayer;
import cz.hatoff.footmen.network.mesage.slot.SlotAssignment;


public class MessageSerializer {
    
    public static void initializeSerializables() {
        Serializer.registerClass(MoveRequest.class);
        Serializer.registerClass(MoveResponse.class);
        Serializer.registerClass(SlotAssignment.class);
        Serializer.registerClass(AddNewPlayer.class);
    }
    
}
