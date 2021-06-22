package org.ddd.event;

import java.time.Instant;

/**
 * @author Michael
 */
public interface Event {
    String getEventId();

    Object getSource();

    Instant occurredOn();

    default String eventType(){
        return this.getClass().getTypeName();
    }
}
