package edu.gordon.simulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller class for the ATMPanel
 */
public class ATMPanelController {

   public ATMPanelController(final GUI gui, ATMPanel panel ){

       Button showLogButton = panel.getShowLogButton();

       showLogButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
               gui.showCard("LOG");
           }
       });
   }
}
