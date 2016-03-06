package edu.gordon.controller;

import com.google.common.eventbus.Subscribe;
import edu.gordon.events.ClearDisplayEvent;
import edu.gordon.events.InsertedCardEvent;
import edu.gordon.model.Card;
import edu.gordon.model.Money;
import edu.gordon.physical.EventBusManager;
import edu.gordon.view.GUI;

import java.util.Observable;

/**
 * Created by Iron_Cesar on 16-02-03.
 */
public class MainController{
    private SimCardReaderController cardReaderController;
    private SimEnvelopeAcceptorController envelopeAcceptorController;
    private SimKeyboardController keyboardController;
    private SimOperatorPanelController simOperatorPanelController;
    private SimDisplayController simDisplayController;
    private SimCashDispenserController simCashDispenserController;
    private SimReceiptPrinterController simReceiptPrinterController;
    private GUI gui;

    public MainController() {
        simOperatorPanelController = new SimOperatorPanelController(this);
        cardReaderController = new SimCardReaderController(this);
        simDisplayController = new SimDisplayController();
        simCashDispenserController = new SimCashDispenserController();
        envelopeAcceptorController = new SimEnvelopeAcceptorController();
        simReceiptPrinterController = new SimReceiptPrinterController();
        keyboardController = new SimKeyboardController(simDisplayController.getSimDisplay(),envelopeAcceptorController.getSimEnvelopeAcceptor());

        gui = new GUI(simOperatorPanelController.getPanel(),cardReaderController.getButton(), simDisplayController.getSimDisplay(), keyboardController.getSimKeyboard(),
                simCashDispenserController.getSimCashDispenser(), envelopeAcceptorController.getSimEnvelopeAcceptor(), simReceiptPrinterController.getSimReceiptPrinter());

        EventBusManager.register(simDisplayController);
        EventBusManager.register(cardReaderController);
        EventBusManager.register(simOperatorPanelController);
        EventBusManager.register(gui);
        EventBusManager.register(this);
        EventBusManager.register(simCashDispenserController);
        EventBusManager.register(simReceiptPrinterController);

    }

    /**
     * Method that set the CardReader visible or not
     * @param on boolean

    public void setVisibleCardReader(boolean on){
        cardReaderController.setVisible(on);
    }*/

    /**
     * Method that print a receipt
     * @param text The text to print
     */
    public void printReceipt(String text){
        simReceiptPrinterController.println(text);
    }

    /**
     * Simulate accepting an envelope from customer.
     *
     * return true if an envelope was received within the prescribed time,
     *        else false
     */
    public boolean acceptEnvelope(){
        return envelopeAcceptorController.acceptEnvelope();
    }

    /**
     * Simulate dispensing cash to a customer
     *
     * @param amount the amount of cash to dispense
     *
     * Precondition: amount is <= cash on hand

    public void animateDispensingCash(Money amount){
        simCashDispenserController.animateDispensingCash(amount);
    }
    */
    /**
     * Simulate reading input from the keyboard
     *
     * @param mode the input mode to use - one of the constants defined below.
     * @param maxValue the maximum acceptable value (used in MENU_MODE only)
     * @return the line that was entered - null if user pressed CANCEL.
     */
    public String readInput(int mode, int maxValue){
        return keyboardController.readInput(mode, maxValue);
    }


    /**
     * Simulate reading of a card
     *
     *  @return Card object representing information on the card if read
     *          successfully, null if not read successfully

    public Card readCard(){
        // Machine can't be turned off while there is a card in it
        simOperatorPanelController.setEnabledPanel(false);
        cardReaderController.animateInsertion();

        // Since we don't have a magnetic stripe reader, we'll simulate by
        // having customer type the card number in
        return gui.readCard();
    }*/

/*
    public void switchChangedSimulation(boolean on){
        setVisibleCardReader(on);
        this.switchValue = on;
        setChanged();
        notifyObservers();
        //simulation.switchChanged(on);
    }*/


    /**
     * Notify ATM that a card has been inserted

    public void cardInserted() {
       this.cardInserted=true;
        setChanged();
         notifyObservers();
        //simulation.cardInserted();
    } */

    /**
     * Simulated getting initial amount of cash from operator
     *
     *  @return value of initial cash entered

    public Money getInitialCash(){
        return gui.getInitialCash();
    }*/



    public GUI getGui() {
        return gui;
    }
}

