package edu.gordon.exception;

/**
 * Created by Iron_Cesar on 16-01-27.
 */
public class Cancelled extends Exception {

    public Cancelled()
    {
        super("Cancelled by customer");
    }
}
