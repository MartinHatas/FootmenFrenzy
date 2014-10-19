/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.hatoff.footmen.network.mesage.slot;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class SlotAssignment extends AbstractMessage{
    
    private Long slotId;

    public SlotAssignment() {
    }
    
    public SlotAssignment(Long slotId) {
        this.slotId = slotId;
    }

    public Long getSlotId() {
        return slotId;
    }
   
    
}
