package cz.hatoff.footmen.model;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


public enum Slot {   
    
    RED(0L, new Vector3f(-120, 2, -120), ColorRGBA.Red), 
    BLUE(1L,new Vector3f(-120, 2, -110), ColorRGBA.Blue), 
    GREEN(2L, new Vector3f(-110, 2, -120), ColorRGBA.Green); 
    
    private final Long slotId;
    private final Vector3f baseVector;
    private final ColorRGBA color;

    private Slot(Long slotId, Vector3f baseVector, ColorRGBA color) {
        this.slotId = slotId;
        this.baseVector = baseVector;
        this.color = color;
    }

    public Long getSlotId() {
        return slotId;
    }

    public Vector3f getBaseVector() {
        return baseVector;
    }

    public ColorRGBA getColor() {
        return color;
    }      
    
    public static Slot getSlotById(Long id) {
        for(Slot slot: Slot.values()) {
            if(slot.getSlotId() == id) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Invalid slot number.");
    }

    @Override
    public String toString() {
        return "Slot{" + "slotId=" + slotId + ", baseVector=" + baseVector + ", color=" + color + '}';
    }
  
}
