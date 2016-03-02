package edu.gordon.events;

/**
 * Created by Iron_Cesar on 16-03-02.
 */
public class DisplayMessageEvent {

    //Message to display
    public String message="";

    /**
     * Default constructor
     */
    public DisplayMessageEvent(){
    }

    /**
     * Default constructor
     */
    public DisplayMessageEvent(String message){
        this.message = message;
    }
}
