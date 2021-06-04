package org.ddd.event.domain;

import java.time.Instant;

/**
 * @author Michael
 */
public interface Event {
    String getEventId();

    String eventType();

    Object getSource();

    Instant occurredOn();
}
