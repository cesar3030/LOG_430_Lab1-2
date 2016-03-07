package edu.gordon.utils;

import com.google.common.eventbus.EventBus;

/**
 * class that encapsulate the EventBus instance for an easy register
 */
public class EventBusManager {

    private static final EventBus eventBus = new EventBus();

    private EventBusManager(){}

    /**
     * Register the given object to the event bus
     * @param o
     */
    public static void register(Object o){
        eventBus.register(o);
    }

    /**
     * Post an event in the event bus
     * @param o the event
     */
    public static void post(Object o){
        eventBus.post(o);
    }


}
