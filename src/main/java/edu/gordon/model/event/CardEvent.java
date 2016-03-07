package edu.gordon.model.event;

import edu.gordon.model.Card;

/**
 * Created by Iron_Cesar on 16-03-02.
 */
public class CardEvent {
    //Re-enable on-off switch on the Operator Panel
    protected Boolean enablePanel;

    //The card being manipulate
    public Card card =null;

    /**
     * Default constructor
     */
    public CardEvent(){}

    /**
     * Constructor with Card Attribute
     * @param card
     */
    public CardEvent(Card card){
        this.card = card;
    }

    public Boolean isEnablePanel() {
        return enablePanel;
    }

    public void setEnablePanel(Boolean enablePanel) {
        this.enablePanel = enablePanel;
    }
}
