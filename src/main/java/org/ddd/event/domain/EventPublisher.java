package org.ddd.event.domain;

/**
 * @Author Michael
 * @Date 2021/5/26 10:53
 */
public interface EventPublisher {
    void publishEvent(Event event);
}
