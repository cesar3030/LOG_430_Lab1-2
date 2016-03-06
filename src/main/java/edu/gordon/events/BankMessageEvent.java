package edu.gordon.events;

import edu.gordon.model.Balances;
import edu.gordon.model.Message;
import edu.gordon.model.Status;

/**
 * Created by César Jeanroy on 16-03-06.
 * Event use to send a message to the bank and to save the status returned by the bank.
 */
public class BankMessageEvent {

    //The message to send
    public Message message;
    //Balances in customer's account
    public Balances balances;
    //The status returned by the bank
    public Status status;

    /**
     * Constructor with parameters
     * @param message   The message to send
     * @param balances (out) balances in customer's account as reported by bank
     */
    public BankMessageEvent(Message message, Balances balances) {
        this.message = message;
        this.balances = balances;
    }
}
