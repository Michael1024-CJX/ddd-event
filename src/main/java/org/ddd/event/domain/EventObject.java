package org.ddd.event.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * @Author Michael
 */
public abstract class EventObject implements Event{
    protected String eventId = UUID.randomUUID().toString();
    protected String eventType;
    protected Object source;
    protected Instant occurredTime;

    public EventObject(Object source) {
        this.source = source;
        this.eventType = this.getClass().getTypeName();
        this.occurredTime = Instant.now();
    }

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public Instant occurredOn() {
        return occurredTime;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String eventType() {
        return eventType;
    }
}
