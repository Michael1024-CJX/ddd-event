package org.ddd.event.domain;

/**
 * @Author Michael
 */
public interface EventSubscriberRegister {
    void registerSubscriber(Class<?> aClass, EventSubscriber eventSubscriber);
}
