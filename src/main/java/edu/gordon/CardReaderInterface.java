package edu.gordon;

import edu.gordon.banking.Card;

/**
 * Created by Iron_Cesar on 16-01-27.
 */
public interface CardReaderInterface {

    /** Read a card that has been partially inserted into the reader
     *
     *  @return Card object representing information on the card if read
     *          successfully, null if not read successfully
     */
    public Card readCard();

    /** Eject the card that is currently inside the reader.
     */
    public void ejectCard();

    /** Retain the card that is currently inside the reader for action by the
     *  bank.
     */
    public void retainCard();
}
