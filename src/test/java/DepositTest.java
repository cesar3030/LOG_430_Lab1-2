package edu.gordon.physical;

import edu.gordon.model.AccountInformation;
import edu.gordon.model.Card;
import edu.gordon.model.Message;
import edu.gordon.model.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class DepositTest {

    private ATM atm;
    private Card card;
    private CustomerConsoleInterface customerConsole;

    private int pin = 1234;
    private int from = -1;
    private int to = 1;
    private Money money = new Money(456);
    private Deposit deposit;
    private EnvelopeAcceptorInterface envelopeAcceptor;
    private NetworkToBankInterface networkToBank;

    @Before
    public void init()throws Exception{

        atm =mock(ATM.class);
        card = mock(Card.class);
        customerConsole =mock(CustomerConsoleInterface.class);
        deposit = new Deposit(atm,card,pin);
        envelopeAcceptor = mock(EnvelopeAcceptorInterface.class);
        networkToBank = mock(NetworkToBankInterface.class);


        Mockito.when(atm.getCustomerConsole()).thenReturn(customerConsole);
        Mockito.when(atm.getEnvelopeAcceptor()).thenReturn(envelopeAcceptor);
        Mockito.when(atm.getNetworkToBank()).thenReturn(networkToBank);
        Mockito.when(customerConsole.readMenuChoice("Account to deposit to", AccountInformation.ACCOUNT_NAMES)).thenReturn(to);
        Mockito.when(customerConsole.readAmount("Amount to deposit")).thenReturn(money);

    }

    @Test
    public void testGetSpecificsFromCustomer() throws Exception {

        Message message = deposit.getSpecificsFromCustomer();

        //We make sure that some functions have been called on mocks
        Mockito.verify(atm, Mockito.times(2)).getCustomerConsole();
        Mockito.verify(customerConsole, Mockito.times(1)).readMenuChoice("Account to deposit to",AccountInformation.ACCOUNT_NAMES);
        Mockito.verify(customerConsole,Mockito.times(1)).readAmount("Amount to deposit");

        //We check teh validity of the values
        assertEquals(message.getPIN(), pin);
        assertEquals(message.getAmount().toString(),money.toString());
        assertEquals(message.getFromAccount(),from);
        assertEquals(message.getToAccount(),to);
        assertEquals(message.getMessageCode(), Message.INITIATE_DEPOSIT);

    }

    @Test
    public void testCompleteTransaction() throws Exception {

        //We create the message that must contain the receipt
        String[] detailsPortionTest = new String[2];
        detailsPortionTest[0] = "DEPOSIT TO: " +
                AccountInformation.ACCOUNT_ABBREVIATIONS[to];
        detailsPortionTest[1] = "AMOUNT: " + money.toString();

        //We complete the transaction and generate the receipt
        deposit.getSpecificsFromCustomer();
        Receipt receipt = deposit.completeTransaction();

        assertEquals(receipt.detailsPortion[0],detailsPortionTest[0]);
        assertEquals(receipt.detailsPortion[1],detailsPortionTest[1]);
    }
}