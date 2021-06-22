package org.ddd.event;

import java.util.Set;

/**
 * @author Michael
 */
public interface EventSubscriberRegister {
    void registerSubscriber(Class<?> aClass, EventSubscriber eventSubscriber);

    Set<EventSubscriber> getSubscribers(String eventType);
}
