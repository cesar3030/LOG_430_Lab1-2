package edu.gordon.events;

import edu.gordon.model.Money;

/**
 * Created by Iron_Cesar on 16-03-06.
 * Event used during the initialisation of the ATM to know how much money does it have.
 */
public class InitialCashEvent {
    //The initial amount of money in the ATM
    public Money amount;
}
