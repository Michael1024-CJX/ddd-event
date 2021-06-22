package org.ddd.event;

/**
 * @author Michael
 */
public interface EventSubscriber<E extends Event> {
    void handle(E event);
}
