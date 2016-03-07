package edu.gordon.model.event;

/**
 * Created by Cesar Jeanroy on 16-03-03.
 * Method that is use to turn ON or OFF the ATM
 */
public class SwitchStateEvent {
    //ATM State
    private boolean turnOn;

    public SwitchStateEvent(boolean turnOn){
        this.turnOn = turnOn;
    }

    /**
     * Method that return if the ATM is Turn On or not
     * @return
     */
    public boolean isTurnOn() {
        return turnOn;
    }
}
