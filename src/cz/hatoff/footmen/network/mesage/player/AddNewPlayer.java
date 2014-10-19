
package cz.hatoff.footmen.network.mesage.player;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class AddNewPlayer extends AbstractMessage{
    
    private Long slotId;

    public AddNewPlayer() {
    }
    

    public AddNewPlayer(Long slotId) {
        this.slotId = slotId;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    } 
    
}
