package edu.gordon.controller;

import edu.gordon.view.SimReceiptPrinter;

import java.awt.*;

/**
 * Created by Iron_Cesar on 16-02-03.
 */
public class SimReceiptPrinterController {
    private SimReceiptPrinter simReceiptPrinter;

    public SimReceiptPrinterController() {
        this.simReceiptPrinter = new SimReceiptPrinter();
    }

    /** Print line to receipt
     *
     *  @param text the line to print
     */
    public void println(String text)
    {
        TextArea printArea = simReceiptPrinter.getPrintArea();
        Button take = simReceiptPrinter.getTake();

        printArea.append(text + '\n');
        try
        {
            Thread.sleep(1 * 1000);
        }
        catch (InterruptedException e)
        { }

        take.setVisible(true);
    }

    public SimReceiptPrinter getSimReceiptPrinter() {
        return simReceiptPrinter;
    }
}
