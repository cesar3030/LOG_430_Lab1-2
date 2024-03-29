package edu.gordon.controller;

import com.google.common.eventbus.Subscribe;
import edu.gordon.model.event.CardEvent;
import edu.gordon.model.event.SwitchStateEvent;
import edu.gordon.utils.EventBusManager;
import edu.gordon.view.SimOperatorPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller class for the SimOperatorPanel
 */
public class SimOperatorPanelController {


    private SimOperatorPanel panel;

    /**
     * Constructor
     *
     * @param mainController
     */
    public SimOperatorPanelController(final MainController mainController) {
        panel = new SimOperatorPanel();

        final Button button = panel.getButton();
        final Label message = panel.getMessage();
        //final Simulation simulation = panel.getSimulation();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (button.getLabel().equals("OFF"))    // ATM is currently on
                {
                    message.setText("Click button to turn ATM on  ");
                    button.setLabel(" ON ");

                    //mainController.switchChangedSimulation(false);
                    EventBusManager.post(new SwitchStateEvent(false));
                } else                                    // ATM is currently off
                {
                    message.setText("Click button to turn ATM off");
                    button.setLabel("OFF");

                    //mainController.switchChangedSimulation(true);
                    EventBusManager.post(new SwitchStateEvent(true));
                }
            }
        });

        // Use a thread to blink the "Click button to turn ATM on" message when
        // the ATM is off.  This will also make the message invisible when the
        // button is not enabled.

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                    }

                    if (message.isVisible() && !button.getLabel().equals("OFF")
                            || !panel.isEnabled())
                        message.setVisible(false);
                    else
                        message.setVisible(true);
                }
            }
        }.start();
    }


    public SimOperatorPanel getPanel() {
        return panel;
    }

    /**
     * Method that Enable or disable the panel
     *
     * @param value Boolean
     */
    public void setEnabledPanel(Boolean value) {
        panel.setEnabled(value);
    }

    /**
     * Listner that show or hide the SimOperatorPanel
     * @param event
     */
    @Subscribe
    public void listner(CardEvent event) {
        if(event.isEnablePanel()!= null)
            this.setEnabledPanel(event.isEnablePanel());
    }
}
