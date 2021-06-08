package org.ddd.event.domain;

import java.util.List;

/**
 * @author Michael
 */
public interface EventStorage {
    void storeEvent(StorableEvent event);

    StorableEvent find(String eventId);

    List<StorableEvent> findNotFinishEvent();
}
