package edu.gordon.model.event;

/**
 * Created by Cesar Jeanroy on 16-03-02.
 *
 * Event that trigger the read card operation
 */
public class ReadCardEvent extends CardEvent{

    /**
     * Constructor with enablePanel parameter
     */
    public ReadCardEvent(Boolean enablePanel) {
        super();
        super.enablePanel = enablePanel;
    }

}
