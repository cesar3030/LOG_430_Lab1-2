package edu.gordon.simulation;

import edu.gordon.view.simulation.SimOperatorPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller class for the SimOperatorPanel
 */
public class SimOperatorPanelController {


    /**
     * Constructor
     * @param panel The panel linked to that controller
     */
    public SimOperatorPanelController (final SimOperatorPanel panel) {

        final Button button = panel.getButton();
        final Label  message = panel.getMessage();
        final Simulation simulation = panel.getSimulation();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (button.getLabel().equals("OFF"))    // ATM is currently on
                {
                    message.setText("Click button to turn ATM on  ");
                    button.setLabel(" ON ");

                    simulation.switchChanged(false);
                }
                else                                    // ATM is currently off
                {
                    message.setText("Click button to turn ATM off");
                    button.setLabel("OFF");

                    simulation.switchChanged(true);
                }
            }
        });

        // Use a thread to blink the "Click button to turn ATM on" message when
        // the ATM is off.  This will also make the message invisible when the
        // button is not enabled.

        new Thread() {
            public void run()
            {
                while(true)
                {
                    try
                    {
                        sleep(1000);
                    }
                    catch(InterruptedException e)
                    { }

                    if (message.isVisible() && ! button.getLabel().equals("OFF")
                            || ! panel.isEnabled() )
                        message.setVisible(false);
                    else
                        message.setVisible(true);
                }
            }
        }.start();
    }

}
