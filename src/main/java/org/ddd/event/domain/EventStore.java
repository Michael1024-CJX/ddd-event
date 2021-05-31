package org.ddd.event.domain;

/**
 * @Author Michael
 * @Date 2021/5/26 10:56
 */
public interface EventStore {
    void storeEvent(StorableEvent event);

    StorableEvent find(String eventId);
}
