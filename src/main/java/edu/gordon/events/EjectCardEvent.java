package edu.gordon.events;

/**
 * Created by Cesar Jeanroy on 16-03-02.
 *
 * Event that trigger the card ejection animation and update the state of the OperatorPanel
 */
public class EjectCardEvent {
    //Re-enable on-off switch on the Operator Panel
    public boolean enablePanel=true;

    /**
     * Default constructor
     */
    public EjectCardEvent() {
    }

    /**
     * Constructor with enablePanel parameter
     * @param enablePanel
     */
    public EjectCardEvent(boolean enablePanel) {
        this.enablePanel = enablePanel;
    }
}
