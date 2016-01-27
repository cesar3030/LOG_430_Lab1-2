package edu.gordon.simulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Iron_Cesar on 16-01-26.
 */
public class SimEnvelopeAcceptorController {

  private final SimEnvelopeAcceptor simEnvelopeAcceptor;

    /**
     * Constructor
     */
  public SimEnvelopeAcceptorController(final SimEnvelopeAcceptor simEnvelopeAcceptor){
      this.simEnvelopeAcceptor = simEnvelopeAcceptor;

      this.simEnvelopeAcceptor.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)
          {
              synchronized(SimEnvelopeAcceptorController.this) //SimEnvelopeAcceptor.this <- default arg
              {
                  inserted = true;
                  simEnvelopeAcceptor.notify();
              }
          }
      });

  }

    /** Simulate accepting an envelope from customer.
     *
     *  return true if an envelope was received within the prescribed time,
     *         else false
     */
    public synchronized boolean acceptEnvelope()
    {
        inserted = false;
        this.simEnvelopeAcceptor.setVisible(true);

        // Wait for user to simulate inserting envelope by clicking button.
        // If we wait 20 seconds and no envelope is entered, we time out

        try
        {
            wait(MAXIMUM_WAIT_TIME);
        }
        catch(InterruptedException e)
        { }

        if (inserted)
        {
            // Animate envelope going into the machine

            Rectangle originalBounds = this.simEnvelopeAcceptor.getBounds();

            Rectangle currentBounds =
                    new Rectangle(originalBounds.x, originalBounds.y,
                            originalBounds.width, originalBounds.height);

            while (currentBounds.width > 0 && currentBounds.height > 0)
            {
                this.simEnvelopeAcceptor.setBounds(currentBounds.x, currentBounds.y,
                        currentBounds.width, currentBounds.height);
                this.simEnvelopeAcceptor.repaint();
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

            this.simEnvelopeAcceptor.setVisible(false);
            this.simEnvelopeAcceptor.setBounds(originalBounds);
        }
        else
        {
            this.simEnvelopeAcceptor.setVisible(false);
        }
        return inserted;
    }

    /** Inform the envelope acceptor that the customer has pressed the CANCEL
     *  key.  Ignored if not waiting for an envelope
     */
    public synchronized void cancelRequested()
    {
        notify();       // End the wait for the envelope - inserted will still
        // be false so acceptEnvelope() will return false
    }

    /** Becomes true when an envelope has been inserted
     */
    private boolean inserted;

    /** Maximum time to wait for an envelope before timing out - in milliseconds
     */
    private static long MAXIMUM_WAIT_TIME = 20 * 1000;
}
