package edu.gordon.events;

/**
 * Created by César Jeanroy on 16-03-06.
 * Event use to log the given string into the application log
 */
public class LogEvent {

    //Log message
    public String message;

    /**
     * Constructor this parameter
     * @param message The message to log
     */
    public LogEvent(String message) {
        this.message = message;
    }
}
