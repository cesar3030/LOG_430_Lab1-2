package edu.gordon.physical;

import edu.gordon.model.AccountInformation;
import edu.gordon.model.Card;
import edu.gordon.model.Message;
import edu.gordon.model.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class InquiryTest {

    private ATM atm;
    private Card card;
    private CustomerConsoleInterface customerConsole;

    private int pin = 1234;
    private int from = 0;
    private int to = -1;
    private Money money = new Money(0);
    private Inquiry inquiry;

    @Before
    public void init()throws Exception{

        atm =mock(ATM.class);
        card = mock(Card.class);
        customerConsole =mock(CustomerConsoleInterface.class);
        inquiry = new Inquiry(atm,card,pin);

        Mockito.when(atm.getCustomerConsole()).thenReturn(customerConsole);
        Mockito.when(customerConsole.readMenuChoice("Account to inquire from", AccountInformation.ACCOUNT_NAMES)).thenReturn(from);

    }

    @Test
    public void testGetSpecificsFromCustomer() throws Exception {

        Message message = inquiry.getSpecificsFromCustomer();

        //We make sure that some functions have been called on mocks
        Mockito.verify(atm, Mockito.times(1)).getCustomerConsole();
        Mockito.verify(customerConsole, Mockito.times(1)).readMenuChoice("Account to inquire from",AccountInformation.ACCOUNT_NAMES);

        //We check teh validity of the values
        assertEquals(message.getPIN(),pin);
        assertEquals(message.getAmount().toString(),money.toString());
        assertEquals(message.getFromAccount(),from);
        assertEquals(message.getToAccount(),to);
        assertEquals(message.getMessageCode(), Message.INQUIRY);

    }

    @Test
    public void testCompleteTransaction() throws Exception {
        //We create the message that must contain the receipt
        String[] detailsPortionTest = new String[2];
        detailsPortionTest[0] = "INQUIRY FROM: " + AccountInformation.ACCOUNT_ABBREVIATIONS[from];
        detailsPortionTest[1] = "";


        //We complete the transaction and generate the receipt
        inquiry.getSpecificsFromCustomer();
        Receipt receipt = inquiry.completeTransaction();

        assertEquals(receipt.detailsPortion[0],detailsPortionTest[0]);
        assertEquals(receipt.detailsPortion[1],detailsPortionTest[1]);
    }
}