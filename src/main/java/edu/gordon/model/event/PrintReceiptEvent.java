package edu.gordon.model.event;

import java.util.ArrayList;

/**
 * Created by César Jeanroy on 16-03-06.
 * Event that contain the receipt needed to be print
 */
public class PrintReceiptEvent {

    //List of string that represent each line of the receipt
    public ArrayList<String> receiptLines;

    /**
     * Constructor with parameter
     * @param receiptLines Receipt to print out
     */
    public PrintReceiptEvent(ArrayList<String> receiptLines) {
        this.receiptLines = receiptLines;
    }
}
