package org.ddd.event.domain;

import java.util.Objects;

/**
 * @author chenjx
 */
public class SubscriberId {
    private String eventId;
    private String subscriberType;

    public SubscriberId(String eventId, String subscriberType) {
        this.eventId = eventId;
        this.subscriberType = subscriberType;
    }

    public String eventId() {
        return eventId;
    }

    public String subscriberType() {
        return subscriberType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriberId that = (SubscriberId) o;
        return Objects.equals(eventId, that.eventId) &&
                Objects.equals(subscriberType, that.subscriberType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, subscriberType);
    }
}
