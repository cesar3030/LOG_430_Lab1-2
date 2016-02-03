package edu.gordon.physical;

import edu.gordon.exception.Cancelled;

/**
 * Created by Iron_Cesar on 16-01-27.
 */
public interface EnvelopeAcceptorInterface {

    /** Accept an envelope from customer.
     *
     *  @exception edu.gordon.exception.Cancelled if operation timed out or the
     *             customer cancelled it
     */
    public void acceptEnvelope() throws Cancelled;

}
