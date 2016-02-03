package edu.gordon.controller.simulation;

import edu.gordon.physical.Simulation;
import edu.gordon.view.SimDisplay;
import edu.gordon.view.SimEnvelopeAcceptor;
import edu.gordon.view.SimKeyboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Iron_Cesar on 16-01-26.
 */
public class SimKeyboardController {

    /** The panel linked to this controller
     */
    SimKeyboard simKeyboard;

    /** The display onto which to echo input
     */
    private SimDisplay display;

    /** The envelope acceptor to notify if CANCEL is pressed while not awaiting
     *  customer input
     */
    private SimEnvelopeAcceptor envelopeAcceptor;

    /** Current input mode - one of the values defined below
     */
    private int mode;

    /** Not currently reading input - ignore keys (except CANCEL)
     */
    private static final int IDLE_MODE = 0;

    /** Read input in PIN mode - allow user to enter several characters,
     *  and to clear the line if the user wishes; echo as asterisks
     */
    private static final int PIN_MODE = Simulation.PIN_MODE;

    /** Read input in amount mode - allow user to enter several characters,
     *  and to clear the line if the user wishes; echo what use types
     */
    private static final int AMOUNT_MODE = Simulation.AMOUNT_MODE;

    /** Read input in menu choice mode - wait for one digit key to be pressed,
     *  and return value immediately.
     */
    private static final int MENU_MODE = Simulation.MENU_MODE;

    /** Current partial line of input
     */
    private StringBuffer currentInput;

    /** Cancellation flag - set to true if user cancels
     */
    private boolean cancelled;

    /** Maximum valid value - used in MENU_MODE only
     */
    private int maxValue;

    /**
     * Constructor
     *
     *  @param display the display on which to echo typed input
     *  @param envelopeAcceptor - to be notified if cancel is pressed
     *
     */
    public SimKeyboardController(SimKeyboard simKeyboard,SimDisplay display,SimEnvelopeAcceptor envelopeAcceptor) {

        this.display = display;
        this.envelopeAcceptor = envelopeAcceptor;
        this.simKeyboard = simKeyboard;

        Button[] digitKey = simKeyboard.getDigitKey();

        for (int i = 0; i < 10; i ++)
            digitKey[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    digitKeyPressed(Integer.parseInt(e.getActionCommand()));
                }
            });

        simKeyboard.getEnterKey().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterKeyPressed();
            }
        });
        simKeyboard.getClearKey().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearKeyPressed();
            }
        });
        simKeyboard.getCancelKey().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelKeyPressed();
            }
        });

        simKeyboard.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {
                char keyChar = e.getKeyChar();
                int keyCode = e.getKeyCode();
                if (keyChar >= '0' && keyChar <= '9')
                {
                    digitKeyPressed(keyChar - '0');
                    e.consume();
                }
                else
                {
                    switch (keyCode)
                    {
                        case KeyEvent.VK_ENTER:

                            enterKeyPressed();
                            break;

                        case KeyEvent.VK_CLEAR:

                            clearKeyPressed();
                            break;

                        case KeyEvent.VK_CANCEL:
                        case KeyEvent.VK_ESCAPE:

                            cancelKeyPressed();
                            break;
                    }
                    e.consume();
                }
            }
        });

        currentInput = new StringBuffer();
        mode = IDLE_MODE;
    }

    /** Read input from the keyboard
     *
     *  @param mode the input mode to use - one of the constants defined below.
     *  @param maxValue the maximum acceptable value (used in MENU_MODE only)
     *  @return the line that was entered - null if user pressed CANCEL.
     */
    public synchronized String readInput(int mode, int maxValue)
    {
        this.mode = mode;
        this.maxValue = maxValue;
        currentInput.setLength(0);
        cancelled = false;
        if (mode == AMOUNT_MODE)
            setEcho("0.00");
        else
            setEcho("");
        simKeyboard.requestFocus();

        try
        {
            wait();
        }
        catch(InterruptedException e)
        { }

        this.mode = IDLE_MODE;

        if (cancelled)
            return null;
        else
            return currentInput.toString();
    }

    /** Handle a digit key
     *
     *  @param digit the value on the key
     */
    private synchronized void digitKeyPressed(int digit)
    {
        switch (mode)
        {
            case IDLE_MODE:

                break;

            case PIN_MODE:
            {
                currentInput.append(digit);
                StringBuffer echoString = new StringBuffer();
                for (int i = 0; i < currentInput.length(); i ++)
                    echoString.append('*');
                setEcho(echoString.toString());
                break;
            }

            case AMOUNT_MODE:
            {
                currentInput.append(digit);
                String input = currentInput.toString();
                if (input.length() == 1)
                    setEcho("0.0" + input);
                else if (input.length() == 2)
                    setEcho("0." + input);
                else
                    setEcho(input.substring(0, input.length() - 2) + "." +
                            input.substring(input.length() - 2));
                break;
            }

            case MENU_MODE:
            {
                if (digit > 0 && digit <= maxValue)
                {
                    currentInput.append(digit);
                    notify();
                }
                else
                    simKeyboard.getToolkit().beep();
                break;
            }
        }
    }

    /** Handle the ENTER key
     */
    private synchronized void enterKeyPressed()
    {
        switch(mode)
        {
            case IDLE_MODE:

                break;

            case PIN_MODE:
            case AMOUNT_MODE:

                if (currentInput.length() > 0)
                    notify();
                else
                    simKeyboard.getToolkit().beep();
                break;

            case MENU_MODE:

                simKeyboard.getToolkit().beep();
                break;
        }
    }

    /** Handle the CLEAR key
     */
    private synchronized void clearKeyPressed()
    {
        switch(mode)
        {
            case IDLE_MODE:

                break;

            case PIN_MODE:

                currentInput.setLength(0);
                setEcho("");
                break;

            case AMOUNT_MODE:

                currentInput.setLength(0);
                setEcho("0.00");
                break;

            case MENU_MODE:

                simKeyboard.getToolkit().beep();
                break;
        }
    }

    /** Handle the CANCEL KEY
     */
    private synchronized void cancelKeyPressed()
    {
        switch(mode)
        {
            case IDLE_MODE:

                // It is possible to press the cancel key when requested
                // to insert an envelope - so notify the envelope acceptor
                // of this fact (notification is ignored if acceptor is
                // not waiting for an envelope)

                synchronized(envelopeAcceptor)
                {
                    envelopeAcceptor.notify();
                }

            case PIN_MODE:
            case AMOUNT_MODE:
            case MENU_MODE:

                cancelled = true;
                notify();
        }
    }

    /** Set the echo string displayed on the display
     *
     *  @param echo the text to set the echo to (the whole line)
     */
    private void setEcho(String echo)
    {
        display.setEcho(echo);
    }


}
