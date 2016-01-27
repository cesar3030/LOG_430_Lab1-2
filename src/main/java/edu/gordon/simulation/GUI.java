/* * ATM Example system - file GUI.java    * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.simulation;import java.awt.CardLayout;import java.awt.Color;import java.awt.GridBagConstraints;import java.awt.Insets;import java.awt.Panel;import edu.gordon.banking.Card;import edu.gordon.banking.Money;     /** The GUI for the ATM edu.gordon.simulation  */class GUI extends Panel{    /** Constructor.     *     *  @param operatorPanel the edu.gordon.simulation of the card reader     *  @param cardReader the edu.gordon.simulation of the card reader     *  @param display the edu.gordon.simulation of the display     *  @param keyboard the edu.gordon.simulation of the keyboard     *  @param cashDispenser the edu.gordon.simulation of the cash dispenser     *  @param receiptPrinter the edu.gordon.simulation of the receipt printer     */    GUI(SimOperatorPanel operatorPanel,        SimCardReader cardReader,        SimDisplay display,        SimKeyboard keyboard,        SimCashDispenser cashDispenser,        SimEnvelopeAcceptor envelopeAcceptor,        SimReceiptPrinter receiptPrinter)    {        setBackground(Color.lightGray);                // The overall GUI is a panel that uses a card layout, with four cards:        //        // - The card representing the ATM itself        // - A card simulating the facilty on the operator panel that allows        //   the operator to enter the number of bills in the machine (or        //   perhaps to sense this)        // - A card simulating the reading of the magnetic stripe on the        //   customer's ATM card        // - A card displaying the internal log        //        // Normally, the ATM card is displayed.  The initial money entry card        // is displayed during the startup sequence.  The magnetic stripe reader        // card is displayed when a card is inserted.  The log card is displayed        // when the user clicks "Show Log" and is dismissed when the user clicks        // "Hide log".                mainLayout = new CardLayout(5,5);        setLayout(mainLayout);                atmPanel = new ATMPanel(this,                                 operatorPanel, cardReader,                                 display, keyboard,                                cashDispenser, envelopeAcceptor, receiptPrinter);        this.add(atmPanel, "ATM");        billsPanel = new BillsPanel();        add(billsPanel, "BILLS");                cardPanel = new CardPanel();        add(cardPanel, "CARD");        logPanel = new LogPanel(this);        add(logPanel, "LOG");        mainLayout.show(this, "ATM");        /**         * Instanciation of panels's controller         */        ATMPanelController atmPanelController = new ATMPanelController(this,atmPanel);    }    /** Simulate getting the amount of cash in the cash dispenser from the operator     *  at start up     *     *  @return dollar value of the bills in the cash dispenser (# of bills x $20)     */        public Money getInitialCash()    {        mainLayout.show(this, "BILLS");        int numberOfBills = billsPanel.readBills();        mainLayout.show(this, "ATM");                return new Money(20 * numberOfBills);    }        /** Simulate reading of a card     *     *     *  @return Card object representing information on the card if read     *          successfully, null if not read successfully     */    public Card readCard()    {        mainLayout.show(this, "CARD");        int cardNumber = cardPanel.readCardNumber();        mainLayout.show(this, "ATM");                if (cardNumber > 0)            return new Card(cardNumber);        else            return null;    }        /** Simulate printing a line to the log     *     *  @param text the line to print     */    public void printLogLine(String text)    {        logPanel.println(text);    }        /** Show a specific card     *     *  @param cardName the name of the card to show     */    void showCard(String cardName)    {        mainLayout.show(this, cardName);    }        /** Create a GridBagConstraints object with specified constraints, and      *  others defaulted.     *     *  @param row the row (y coordinate)     *  @param col the column (x coordinate)     *  @param width the width     *  @param height the height     *  @param fill the fill     *  @return GridBagConstraints object representing the above     */   static GridBagConstraints makeConstraints(                    int row, int col, int width, int height, int fill)    {         GridBagConstraints g = new GridBagConstraints();        g.gridy = row;        g.gridx = col;        g.gridheight = height;        g.gridwidth = width;        g.fill = fill;        g.insets = new Insets(2,2,2,2);        g.weightx = 1;        g.weighty = 1;        g.anchor = GridBagConstraints.CENTER;        return g;    }    /** The card layout for this panel     */    private CardLayout mainLayout;        /** The panel displaying the ATM itself     */    private ATMPanel atmPanel;        /** The panel asking the operator to enter the number of bills     */    private BillsPanel billsPanel;        /** The panel asking the user to enter the number of the ATM Card     */    private CardPanel cardPanel;        /** The panel displaying the log     */    private LogPanel logPanel;}