package edu.gordon.physical;

import edu.gordon.model.AccountInformation;
import edu.gordon.model.Card;
import edu.gordon.model.Message;
import edu.gordon.model.Money;
import edu.gordon.physical.ATM;
import edu.gordon.physical.CustomerConsoleInterface;
import edu.gordon.physical.Transfer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

public class TransferTest {

    private ATM atm;
    private Card card;
    private CustomerConsoleInterface customerConsole;

    private int pin = 1234;
    private int from = 0;
    private int to = 1;
    private int amount = 9999999;
    private Money money = new Money(amount);
    private Transfer transfer;

    @Before
    public void init()throws Exception{

        atm =mock(ATM.class);
        card = mock(Card.class);
        customerConsole =mock(CustomerConsoleInterface.class);
        transfer = new Transfer(atm,card,pin);

        Mockito.when(atm.getCustomerConsole()).thenReturn(customerConsole);
        Mockito.when(customerConsole.readMenuChoice("Account to transfer from", AccountInformation.ACCOUNT_NAMES)).thenReturn(from);
        Mockito.when(customerConsole.readMenuChoice("Account to transfer to", AccountInformation.ACCOUNT_NAMES)).thenReturn(to);
        Mockito.when(customerConsole.readAmount("Amount to transfer")).thenReturn(money);
    }

    @Test
    public void testGetSpecificsFromCustomer() throws Exception {

        Message message = transfer.getSpecificsFromCustomer();

        //We make sure that some functions have been called on mocks
        Mockito.verify(atm, Mockito.times(3)).getCustomerConsole();
        Mockito.verify(customerConsole, Mockito.times(1)).readMenuChoice("Account to transfer from",AccountInformation.ACCOUNT_NAMES);
        Mockito.verify(customerConsole, Mockito.times(1)).readMenuChoice("Account to transfer to",AccountInformation.ACCOUNT_NAMES);
        Mockito.verify(customerConsole, Mockito.times(1)).readAmount(anyString());

        //We check teh validity of the values
        assertEquals(message.getPIN(),pin);
        assertEquals(message.getAmount(),money);
        assertEquals(message.getFromAccount(),from);
        assertEquals(message.getToAccount(),to);
        assertEquals(message.getMessageCode(),Message.TRANSFER);

    }


    @Test
    public void testCompleteTransaction() throws Exception {

        //We create the message that must contain the receipt
        String[] detailsPortionTest = new String[2];
        detailsPortionTest[0] = "TRANSFER FROM: " +  AccountInformation.ACCOUNT_ABBREVIATIONS[from] + " TO: " + AccountInformation.ACCOUNT_ABBREVIATIONS[to] ;
        detailsPortionTest[1] = "AMOUNT: " + money.toString();

        //We complete the transaction and generate the receipt
       transfer.getSpecificsFromCustomer();
       Receipt receipt = transfer.completeTransaction();

        assertEquals(receipt.detailsPortion[0],detailsPortionTest[0]);
        assertEquals(receipt.detailsPortion[1],detailsPortionTest[1]);

    }


}