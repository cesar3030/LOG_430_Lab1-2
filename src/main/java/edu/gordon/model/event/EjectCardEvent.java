package edu.gordon.model.event;

/**
 * Created by Cesar Jeanroy on 16-03-02.
 *
 * Event that trigger the card ejection animation and update the state of the OperatorPanel
 */
public class EjectCardEvent extends CardEvent{

    /**
     * Constructor with enablePanel parameter
     * @param enablePanel
     */
    public EjectCardEvent(Boolean enablePanel) {
        super.enablePanel = enablePanel;
    }
}
