/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.hatoff.footmen.commons;


public enum State {
    
    IDLE(0), RUNNING(1);
    
    private final Long STATE_NUMBER;

    private State(long stateNumber) {
        this.STATE_NUMBER = stateNumber;
    }

    public Long getStateNumber() {
        return STATE_NUMBER;
    }
      
}
