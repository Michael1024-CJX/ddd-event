package org.ddd.event.domain;

/**
 * @author Michae
 */
public interface EventPublisher {
    void publishEvent(Event event);
}
