package edu.gordon.controller;

import com.google.common.eventbus.Subscribe;
import edu.gordon.events.DispenseCashEvent;
import edu.gordon.model.Money;
import edu.gordon.view.SimCashDispenser;

import java.awt.*;

/**
 * Created by Iron_Cesar on 16-02-03.
 */
public class SimCashDispenserController {

    private SimCashDispenser simCashDispenser;

    public SimCashDispenserController() {
        this.simCashDispenser = new SimCashDispenser();
    }

    public SimCashDispenser getSimCashDispenser() {
        return simCashDispenser;
    }

    /** Animate dispensing cash to a customer
     *
     *  @param amount the amount of cash to dispense
     *
     *  Precondition: amount is <= cash on hand
     */
    public void animateDispensingCash(Money amount)
    {
        Label label = simCashDispenser.getLabel();

        label.setText(amount.toString());
        for (int size = 3; size <= 24; size += 1)
        {
            label.setFont(new Font("SansSerif", Font.PLAIN, size));
            label.setVisible(true);
            try
            {
                Thread.sleep(250);
            }
            catch (InterruptedException e)
            { }
            label.setVisible(false);
        }
    }

    /**
     * Event that execute the dispensing cash if amount is <= cash on hand
     * @param event
     */
    @Subscribe
    public void listner(DispenseCashEvent event){
        this.animateDispensingCash(event.amount);
    }


}
