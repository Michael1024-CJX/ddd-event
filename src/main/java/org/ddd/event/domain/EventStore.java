package org.ddd.event.domain;

import java.util.List;

/**
 * @author Michael
 */
public interface EventStore {
    void storeEvent(StorableEvent event);

    StorableEvent find(String eventId);

    List<StorableEvent> findNotFinishEvent();
}
