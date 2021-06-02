package org.ddd.event.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * @Author Michael
 * @Date 2021/5/26 10:53
 */
public abstract class EventObject implements Event{
    protected String eventId = UUID.randomUUID().toString();
    protected Object source;
    protected Instant occurredTime;

    public EventObject(Object source) {
        this.source = source;
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
}
