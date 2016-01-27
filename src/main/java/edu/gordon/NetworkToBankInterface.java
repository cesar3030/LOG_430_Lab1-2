package edu.gordon;

import java.net.InetAddress;

import edu.gordon.atm.physical.Log;
import edu.gordon.banking.Balances;
import edu.gordon.banking.Message;
import edu.gordon.banking.Status;
import edu.gordon.simulation.Simulation;


/** Interface
 */
 
public interface NetworkToBankInterface
{

    /** Open connection to bank at system startup
     */
    public void openConnection();
    
    /** Close connection to bank at system shutdown
     */
    public void closeConnection();
    
    /** Send a message to bank
     *
     *  @param message the message to send
     *  @param balances (out) balances in customer's account as reported
     *         by bank
     *  @return status code returned by bank
     */
    public Status sendMessage(Message message, Balances balances);
    
    // Log into which to record messages
    
    public Log log =null;
    
    // Network address of the bank
    
    public InetAddress bankAddress = null;
}
