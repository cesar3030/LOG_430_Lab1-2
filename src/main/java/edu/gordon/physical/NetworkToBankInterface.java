package edu.gordon.physical;

import edu.gordon.model.Balances;
import edu.gordon.model.Message;
import edu.gordon.model.Status;

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
}
