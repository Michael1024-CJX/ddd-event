package org.ddd.event.domain;

import java.util.UUID;

/**
 * @Author Michael
 * @Date 2021/5/26 10:53
 */
public abstract class EventObject implements Event{
    protected String eventId = UUID.randomUUID().toString();
    protected Object source;
    protected long occurredTime;

    public EventObject(Object source) {
        this.source = source;
        this.occurredTime = System.currentTimeMillis();
    }

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public long occurredOn() {
        return occurredTime;
    }

    @Override
    public String getEventId() {
        return eventId;
    }
}
