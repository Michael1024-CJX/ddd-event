package org.ddd.demo.impl;

import org.ddd.event.domain.EventObject;

/**
 * @author Michael
 * @date 2021/5/29 15:25
 */
public class FailEvent extends EventObject {
    public FailEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "FailEvent{" +
                "eventId='" + eventId + '\'' +
                ", source=" + source +
                ", occurredTime=" + occurredTime +
                '}';
    }
}
