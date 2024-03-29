package edu.gordon.controller;


import com.google.common.eventbus.Subscribe;
import edu.gordon.model.event.EjectCardEvent;
import edu.gordon.model.event.InsertedCardEvent;
import edu.gordon.model.event.ReadCardEvent;
import edu.gordon.model.event.SwitchStateEvent;
import edu.gordon.utils.EventBusManager;
import edu.gordon.view.SimCardReader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Iron_Cesar on 16-01-20.
 */
public class SimCardReaderController {

    /** To animate card insertion/ejection, we change the bounds of this button.
     *  These are the original bounds we ultimately restore to
     */
    private Rectangle originalBounds;

    /**
     * The SimCardReader instance
     */
    private SimCardReader button;


    public SimCardReaderController(final MainController mainController){

        this.button = new SimCardReader();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //mainController.cardInserted();
               EventBusManager.post(new InsertedCardEvent());
            }
        });
    }





    /** Animate card going into the machine
     */
    public void animateInsertion()
    {
        originalBounds = button.getBounds();
        Rectangle currentBounds =
                new Rectangle(originalBounds.x, originalBounds.y,
                        originalBounds.width, originalBounds.height);

        while (currentBounds.width > 0 && currentBounds.height > 0)
        {
            button.setBounds(currentBounds.x, currentBounds.y,
                    currentBounds.width, currentBounds.height);
            button.repaint();
            try
            {

                Thread.sleep(100);
            }
            catch (InterruptedException e)
            { }

            currentBounds.height -= 1;
            currentBounds.width =
                    (originalBounds.width * currentBounds.height) / originalBounds.height;
            currentBounds.x =
                    originalBounds.x + (originalBounds.width - currentBounds.width) / 2;
            currentBounds.y =
                    originalBounds.y + (originalBounds.height - currentBounds.height) / 2;
        }

        button.setVisible(false);
    }

    /** Animate ejecting the card that is currently inside the reader.
     */
    public void animateEjection()
    {
        button.setLabel("Ejecting card");
        button.setVisible(true);

        Rectangle currentBounds =
                new Rectangle(originalBounds.x + originalBounds.width / 2,
                        originalBounds.y + originalBounds.height / 2,
                        originalBounds.width / originalBounds.height, 1);

        while (currentBounds.height <= originalBounds.height &&
                currentBounds.width <= originalBounds.width)
        {
            button.setBounds(currentBounds.x, currentBounds.y,
                    currentBounds.width, currentBounds.height);
            button.repaint();
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            { }
            currentBounds.height += 1;
            currentBounds.width =
                    (originalBounds.width * currentBounds.height) / originalBounds.height;
            currentBounds.x =
                    originalBounds.x + (originalBounds.width - currentBounds.width) / 2;
            currentBounds.y =
                    originalBounds.y + (originalBounds.height - currentBounds.height) / 2;
        }

        button.setLabel("Click to insert card");
    }

    /** Animate retaining the card that is currently inside the reader for action by the
     *  bank.
     */
    public void animateRetention()
    {
        button.setLabel("Click to insert card");
        button.setVisible(true);
    }

    /**
     * Method that set the SimCardReader button visible or not
     * @param value
     */
    public void setVisible(Boolean value){
        button.setVisible(value);
    }

    /**
     * getter
     * @return SimCardReader
     */
    public SimCardReader getButton(){
        return button;
    }

    @Subscribe
    public void listner(EjectCardEvent event){
        this.animateEjection();
    }

    @Subscribe
    public void listner(ReadCardEvent event){
        this.animateInsertion();
    }

    /**
     * Listner that shows or hide the sim card button depending on the ATM state stored in the event
     * @param event
     */
    @Subscribe
    public void listner(SwitchStateEvent event){
        this.setVisible(event.isTurnOn());
    }

}
