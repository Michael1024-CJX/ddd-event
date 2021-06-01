package org.ddd.demo.impl;

import org.ddd.event.domain.EventStore;
import org.ddd.event.domain.StorableEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Michael
 * @date 2021/5/27 22:28
 */
public class DefaultEventStore implements EventStore {
    private Map<String, StorableEvent> inMemoryCache = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public void storeEvent(StorableEvent event) {
        inMemoryCache.put(event.getEventId(), event);
    }

    @Override
    public StorableEvent find(String eventId) {
        return inMemoryCache.get(eventId);
    }

    @Override
    public List<StorableEvent> findNotFinishEvent() {
        return inMemoryCache.values()
                .stream()
                .filter(StorableEvent::isRunning)
                .collect(Collectors.toList());
    }
}
