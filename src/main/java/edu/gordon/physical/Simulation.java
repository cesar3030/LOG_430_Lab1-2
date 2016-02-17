/* * ATM Example system - file Simulation.java * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.physical;import edu.gordon.controller.MainController;import edu.gordon.model.SimulatedBank;import edu.gordon.model.Balances;import edu.gordon.model.Card;import edu.gordon.model.Message;import edu.gordon.model.Money;import edu.gordon.model.Status;import edu.gordon.view.*;/** Simulation of the physical components of the ATM, including its network  *  connection to the bank.  An instance is created at startup by either the *  application's main() program or the applet's init() method. * *  The individual components are displayed in a panel belonging to class GUI. *  The bank is simulated by an object belonging to class SimulatedBank.  The *  constructor for this class creates one instance of each. * *  The static method getInstance() allows components of the ATM to access the one *  and only instance of this class in order to simulate various operations.  The *  remaining methods simulate specific operations of the ATM, and are forwarded *  to either the GUI panel or the simulated bank to actually carry them out. */public class Simulation{    public Simulation(ATM atm)    {        this.atm = atm;        // Create the GUI containing the above        /**         * Instanciation main controller         */        this.mainController = new MainController();        mainController.addObserver(atm);        // Create the edu.gordon.simulation of the bank                simulatedBank = new SimulatedBank();                theInstance = this;    }        /** Accessor for the one and only instance of this class     *     *  @return the instance of this class     */    public static Simulation getInstance()    {        return theInstance;    }        /** Simulated getting initial amount of cash from operator     *     *  @return value of initial cash entered     */    public Money getInitialCash()    {        return mainController.getInitialCash();    }        /** Simulate reading of a card     *     *     *  @return Card object representing information on the card if read     *          successfully, null if not read successfully     */    public Card readCard(){        return mainController.readCard();    }        /** Simulate ejecting a card      */    public void ejectCard(){       mainController.ejectCard();    }        /** Simulate retaining a card     */    public void retainCard(){        mainController.retainCard();    }        /** Clear the simulated display     */    public void clearDisplay(){        mainController.clearDisplay();    }        /** Write one or more lines to the display - beginning just after the     *  last line written     *     *  @param text the text to display     */    public void display(String text){        mainController.display(text);    }         /** Simulate reading input from the keyboard     *     *  @param mode the input mode to use - one of the constants defined below.     *  @param maxValue the maximum acceptable value (used in MENU_MODE only)     *  @return the line that was entered - null if user pressed CANCEL.     */    public String readInput(int mode, int maxValue){        return mainController.readInput(mode, maxValue);    }        /** Simulate dispensing cash to a customer     *     *  @param amount the amount of cash to dispense     *     *  Precondition: amount is <= cash on hand     */    public void dispenseCash(Money amount){        mainController.animateDispensingCash(amount);    }    /** Simulate accepting an envelope from customer.     *     *  return true if an envelope was received within the prescribed time,     *         else false     */    public boolean acceptEnvelope(){        return mainController.acceptEnvelope();    }    /** Simulate printing one line of a receipt     *     *  @param text the line to print     */    public void printReceiptLine(String text){       mainController.printReceipt(text);    }        /** Simulate printing a line to the log     *     *  @param text the line to print     */    public void printLogLine(String text)    {        mainController.printLogLine(text);    }        /** Simulate sending a message to bank     *     *  @param message the message to send     *  @param balances (out) balances in customer's account as reported     *         by bank     *  @return status code returned by bank     */    public Status sendMessage(Message message, Balances balances)    {        // Simulate time taken to send message over network                try        {            Thread.sleep(2 * 1000);        }        catch(InterruptedException e)        { }                return simulatedBank.handleMessage(message, balances);    }    /** Accessor for GUI Panel that simulates the ATM     *     *  @return the GUI Panel     */    public GUI getGUI()    {        return mainController.getGui();    }        /** Accessor for simulated bank     *     *  @return simulated bank     */    public SimulatedBank getSimulatedBank()    {        return simulatedBank;    }    /* Possible values for mode parameter to readInput() */        /** Read input in PIN mode - allow user to enter several characters,     *  and to clear the line if the user wishes; echo as asterisks     */    public static final int PIN_MODE = 1;        /** Read input in amount mode - allow user to enter several characters,     *  and to clear the line if the user wishes; echo what use types     */    public static final int AMOUNT_MODE = 2;        /** Read input in menu choice mode - wait for one digit key to be pressed,     *  and return value immediately.     */    public static final int MENU_MODE = 3;    /** The ATM object for the ATM being simulated     */    private ATM atm;    /** Panel containing the GUI that simulates the ATM     */    private GUI gui;        /** Simulated bank     */    private SimulatedBank simulatedBank;        /** The one and only instance of this class     */    private static Simulation theInstance;    /** The SimCardReader controller     */    private MainController mainController;}