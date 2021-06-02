package org.ddd.event.domain;

/**
 * @Author Michae
 */
public interface EventPublisher {
    void publishEvent(Event event);
}
