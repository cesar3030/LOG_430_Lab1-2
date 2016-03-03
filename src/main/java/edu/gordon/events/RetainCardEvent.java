package edu.gordon.events;

/**
 * Created by Cesar Jeanroy on 16-03-02.
 *
 * Event that trigger the card retention and update the state of the OperatorPanel
 */
public class RetainCardEvent  extends CardEvent{

    /**
     * Constructor with enablePanel parameter
     * @param enablePanel
     */
    public RetainCardEvent(boolean enablePanel) {
        super.enablePanel = enablePanel;
    }

}
