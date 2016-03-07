/* * ATM Example system - file CustomerConsole.java    * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.physical;import edu.gordon.model.event.ClearDisplayEvent;import edu.gordon.model.event.DisplayMessageEvent;import edu.gordon.model.event.ReadInputEvent;import edu.gordon.exception.Cancelled;import edu.gordon.model.Money;import edu.gordon.utils.EventBusManager;/** Manager for the ATM's customer console.  In a real ATM, this would  *  manage a physical device; in this edu.gordon.simulation,  it uses classes  *  in package edu.gordon.simulation to simulate the device (actually two separate *  devices - the display and the keyboard.) */ public class CustomerConsoleSimulation implements CustomerConsoleInterface{    /** Constructor     */    public CustomerConsoleSimulation()    {    }        /** Display a message to the customer     *     *  @param message the message to display     */    public void display(String message)    {        EventBusManager.post(new ClearDisplayEvent());        EventBusManager.post(new DisplayMessageEvent(message));    }        /** Read a PIN entered by the customer (echoed as asterisks)     *     *  @param prompt the message to display prompting the customer to enter PIN     *  @return the PIN that was entered     *  @exception Cancelled if customer presses the CANCEL key before pressing ENTER     */    public int readPIN(String prompt) throws Cancelled    {        //Simulation.getInstance().clearDisplay();        EventBusManager.post(new ClearDisplayEvent());        EventBusManager.post(new DisplayMessageEvent(prompt));        EventBusManager.post(new DisplayMessageEvent(""));        ReadInputEvent readInputEvent = new ReadInputEvent(Simulation.PIN_MODE, 0);        EventBusManager.post(readInputEvent);        EventBusManager.post(new ClearDisplayEvent());                if (readInputEvent.input == null)            throw new Cancelled();        else            return Integer.parseInt(readInputEvent.input);    }        /** Display a menu of options and return choice made by customer     *     *  @param prompt message to display before the list of options     *  @param menu the options     *  @return the number of the option chosen (0 .. # of options - 1)     *  Note: the options are numbered 1 .. # of options when displayed for the     *  customer - but the menu array indices and the final result returned are in     *  the range 0 .. # of options - 1     *     *  @exception Cancelled if customer presses the CANCEL key before choosing option     */    public synchronized int readMenuChoice(String prompt, String[] menu) throws Cancelled    {        EventBusManager.post(new ClearDisplayEvent());        EventBusManager.post(new DisplayMessageEvent(prompt));        for (int i = 0; i < menu.length; i ++)            EventBusManager.post(new DisplayMessageEvent((i+1) + ") " + menu[i]));        ReadInputEvent readInputEvent = new ReadInputEvent(Simulation.MENU_MODE, menu.length);        EventBusManager.post(readInputEvent);        EventBusManager.post(new ClearDisplayEvent());                if (readInputEvent.input == null)            throw new Cancelled();        else            return Integer.parseInt(readInputEvent.input) - 1;    }        /** Read a money amount entered by the customer     *     *  @param prompt the message to display prompting the customer to enter amount     *  @return the amount entered by the customer     *  @exception Cancelled if customer presses the CANCEL key before pressing ENTER     */    public synchronized Money readAmount(String prompt) throws Cancelled    {        EventBusManager.post(new ClearDisplayEvent());        EventBusManager.post(new DisplayMessageEvent(prompt));        EventBusManager.post(new DisplayMessageEvent(""));        ReadInputEvent readInputEvent = new ReadInputEvent(Simulation.AMOUNT_MODE, 0);        EventBusManager.post(readInputEvent);        EventBusManager.post(new ClearDisplayEvent());                if (readInputEvent.input == null)            throw new Cancelled();        else        {            int dollars = Integer.parseInt(readInputEvent.input) / 100;            int cents = Integer.parseInt(readInputEvent.input) % 100;            return new Money(dollars, cents);        }    }}