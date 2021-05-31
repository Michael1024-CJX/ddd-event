package org.ddd.event.domain;

/**
 * @Author Michael
 * @Date 2021/5/26 10:55
 */
public interface EventSubscriberRegister {
    void registerSubscriber(Class<?> aClass, EventSubscriber eventSubscriber);
}
