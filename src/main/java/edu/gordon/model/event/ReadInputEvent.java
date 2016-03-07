package edu.gordon.model.event;

/**
 * Created by César Jeanroy on 16-03-06.
 * Event fired to read an input from the user interface and to store the given input
 */
public class ReadInputEvent {

    //The input mode
    public int mode;
    //The max value acceptable
    public int maxValue;
    //The user input
    public String input;

    /**
     * Constructor with parameter
     *  @param mode the input mode to use - one of the constants defined below.
     *  @param maxValue the maximum acceptable value (used in MENU_MODE only)
     */
    public ReadInputEvent(int mode, int maxValue) {
        this.mode = mode;
        this.maxValue = maxValue;
    }

}
