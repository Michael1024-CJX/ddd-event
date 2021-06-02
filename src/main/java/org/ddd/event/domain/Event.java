package org.ddd.event.domain;

import java.time.Instant;

/**
 * @Author Michael
 */
public interface Event {
    String getEventId();

    Object getSource();

    Instant occurredOn();
}
