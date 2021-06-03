package org.ddd.event.domain;

/**
 * @author Michael
 */
public interface EventSubscriberRegister {
    void registerSubscriber(Class<?> aClass, EventSubscriber eventSubscriber);
}
