package org.ddd.event.domain;

import java.util.List;

/**
 * @Author Michael
 * @Date 2021/5/26 10:56
 */
public interface EventStore {
    void storeEvent(StorableEvent event);

    StorableEvent find(String eventId);

    List<StorableEvent> findNotFinishEvent();
}
