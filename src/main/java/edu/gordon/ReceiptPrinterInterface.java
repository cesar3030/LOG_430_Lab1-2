package edu.gordon;

import edu.gordon.banking.Receipt;

public interface ReceiptPrinterInterface {


    /** Print a receipt
     *
     *  @param receipt object containing the information to be printed
     */
    public void printReceipt(Receipt receipt);
}
