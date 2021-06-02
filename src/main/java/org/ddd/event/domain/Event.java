package org.ddd.event.domain;

import java.time.Instant;

/**
 * @Author Michael
 * @Date 2021/5/30 19:09
 */
public interface Event {
    String getEventId();

    Object getSource();

    Instant occurredOn();
}
