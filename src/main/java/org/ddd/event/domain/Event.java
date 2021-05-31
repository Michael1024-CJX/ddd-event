package org.ddd.event.domain;

/**
 * @Author Michael
 * @Date 2021/5/30 19:09
 */
public interface Event {
    String getEventId();

    Object getSource();

    long occurredOn();
}
