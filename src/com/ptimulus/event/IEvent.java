package com.ptimulus.event;

/**
 * Interface of a event source class. Allow to enable and disable the event source.
 */
public interface IEvent {

    /**
     * Enable the event source.
     */
    public void startListening();

    /**
     * Disable the event source.
     */
    public void stopListening();
}