package edu.gordon.events;

/**
 * Created by Cesar Jeanroy on 16-03-02.
 *
 * Event that trigger the card retention and update the state of the OperatorPanel
 */
public class RetainCardEvent {
    //Re-enable on-off switch on the Operator Panel
    public boolean enablePanel=true;

    /**
     * Default constructor
     */
    public RetainCardEvent() {
    }

    /**
     * Constructor with enablePanel parameter
     * @param enablePanel
     */
    public RetainCardEvent(boolean enablePanel) {
        this.enablePanel = enablePanel;
    }

}
