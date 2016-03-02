package edu.gordon.controller;

import com.google.common.eventbus.Subscribe;
import edu.gordon.events.ClearDisplayEvent;
import edu.gordon.view.SimDisplay;

import java.awt.*;

/**
 * Created by Iron_Cesar on 16-02-03.
 */
public class SimDisplayController {

    private SimDisplay simDisplay;

    public SimDisplayController(){
        simDisplay = new SimDisplay();
    }

    public SimDisplay getSimDisplay() {
        return simDisplay;
    }

    /**
     * Clear the display
     */
    public void clearDisplay(){
        //TODO: Move the method from the view to the controller
        simDisplay.clearDisplay();
    }

    /** Add text to the display - may contain one or more lines delimited by \n
     *
     *  @param text the text to display
     */
    public void display(String text){
        //TODO: Move the method from the view to the controller
        simDisplay.display(text);
    }

    /** Set echoed input on the display
     *
     *  @param echo the line to be echoed - always the entire line
     */
    public void setEcho(String echo) {
        //TODO: Move the method from the view to the controller
        simDisplay.setEcho(echo);
    }

    /** Override the getInsets() method to provide additional spacing all
     *  around
     */
    public Insets getInsets(){
        //TODO: Move the method from the view to the controller
        return simDisplay.getInsets();
    }

    @Subscribe
    public void listner(ClearDisplayEvent event){
        simDisplay.clearDisplay();
    }
}
