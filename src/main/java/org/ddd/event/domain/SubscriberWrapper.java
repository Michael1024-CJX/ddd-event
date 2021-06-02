package org.ddd.event.domain;

import org.ddd.util.GenericSuperclassUtil;

import java.util.Objects;

/**
 * @author Michael
 */
public class SubscriberWrapper {
    private String subscriberType;
    private String eventType;
    private EventSubscriber eventSubscriber;

    public SubscriberWrapper(Class originSubscriberType, EventSubscriber eventSubscriber) {
        this.subscriberType = originSubscriberType.getTypeName();
        this.eventType = GenericSuperclassUtil.getInterfaceT(originSubscriberType, 0).getTypeName();
        this.eventSubscriber = eventSubscriber;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public String getEventType() {
        return eventType;
    }

    public EventSubscriber getEventSubscriber() {
        return eventSubscriber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriberWrapper that = (SubscriberWrapper) o;
        return Objects.equals(subscriberType, that.subscriberType) &&
                Objects.equals(eventType, that.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriberType, eventType);
    }
}
