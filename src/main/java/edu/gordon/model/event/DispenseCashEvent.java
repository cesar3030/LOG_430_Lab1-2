package edu.gordon.model.event;

import edu.gordon.model.Money;

/**
 * Created by César Jeanroy on 16-03-06.
 *
 * Event fired when the ATM needs to dispense Cash.
 */
public class DispenseCashEvent {

    //Amount of money to dispense
    public Money amount;

    /**
     * Constructor with parameter
     * @param amount The amount of money to dispense
     */
    public DispenseCashEvent(Money amount) {
        this.amount = amount;
    }
}
