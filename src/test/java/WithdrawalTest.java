package edu.gordon.physical;

import edu.gordon.model.AccountInformation;
import edu.gordon.model.Card;
import edu.gordon.model.Message;
import edu.gordon.model.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class WithdrawalTest {

    private ATM atm;
    private Card card;
    private CustomerConsoleInterface customerConsole;

    private int pin = 1234;
    private int from = 1;
    private int to = -1;
    private Money money = new Money(20);
    private Withdrawal withdrawal;
    private CashDispenserInterface cashDispenserInterface;
    private  String [] amountOptions;

    @Before
    public void init()throws Exception{

        atm =mock(ATM.class);
        card = mock(Card.class);
        customerConsole =mock(CustomerConsoleInterface.class);
        withdrawal = new Withdrawal(atm,card,pin);
        cashDispenserInterface = mock(CashDispenserInterface.class);
        amountOptions = new String[]{ "$20", "$40", "$60", "$100", "$200" };

        Mockito.when(atm.getCustomerConsole()).thenReturn(customerConsole);
        Mockito.when(atm.getCashDispenser()).thenReturn(cashDispenserInterface);
        Mockito.when(customerConsole.readMenuChoice("Account to withdraw from", AccountInformation.ACCOUNT_NAMES)).thenReturn(from);
        Mockito.when(customerConsole.readMenuChoice("Amount of cash to withdraw",amountOptions)).thenReturn(Mockito.anyInt());
        Mockito.when(cashDispenserInterface.checkCashOnHand(money)).thenReturn(true);
        Mockito.when(customerConsole.readAmount("Amount to withdrawal")).thenReturn(money);


    }

    @Test
    public void testGetSpecificsFromCustomer() throws Exception {

        Message message = withdrawal.getSpecificsFromCustomer();

        //We make sure that some functions have been called on mocks
        Mockito.verify(atm, Mockito.times(2)).getCustomerConsole();
        Mockito.verify(atm, Mockito.times(1)).getCashDispenser();
        Mockito.verify(customerConsole, Mockito.times(1)).readMenuChoice("Account to withdraw from",AccountInformation.ACCOUNT_NAMES);
        Mockito.verify(customerConsole, Mockito.times(1)).readMenuChoice("Amount of cash to withdraw",amountOptions);

        //We check the validity of the values
        assertEquals(message.getPIN(), pin);
        assertEquals(message.getAmount().toString(),money.toString());
        assertEquals(message.getFromAccount(),from);
        assertEquals(message.getToAccount(),to);
        assertEquals(message.getMessageCode(), Message.WITHDRAWAL);

    }

    @Test
    public void testCompleteTransaction() throws Exception {

        //We create the message that must contain the receipt
        String[] detailsPortionTest = new String[2];
        detailsPortionTest[0] = "WITHDRAWAL FROM: " +
                AccountInformation.ACCOUNT_ABBREVIATIONS[from];
        detailsPortionTest[1] = "AMOUNT: " + money.toString();

        //We complete the transaction and generate the receipt
        withdrawal.getSpecificsFromCustomer();
        Receipt receipt = withdrawal.completeTransaction();

        assertEquals(receipt.detailsPortion[0],detailsPortionTest[0]);
        assertEquals(receipt.detailsPortion[1],detailsPortionTest[1]);
    }
}