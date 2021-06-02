package org.ddd.event.domain;

/**
 * @author Michael
 */
public interface EventSubscriber<E extends Event> {
    void handle(E event);
}
