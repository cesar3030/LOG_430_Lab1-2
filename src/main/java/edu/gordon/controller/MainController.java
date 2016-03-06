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
        EventBusManager.register(keyboardController);
        EventBusManager.register(envelopeAcceptorController);

    }

    public GUI getGui() {
        return gui;
    }
}

