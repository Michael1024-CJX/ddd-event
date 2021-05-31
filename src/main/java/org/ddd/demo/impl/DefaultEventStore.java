package org.ddd.demo.impl;

import org.ddd.event.domain.EventStore;
import org.ddd.event.domain.StorableEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael
 * @date 2021/5/27 22:28
 */
public class DefaultEventStore implements EventStore {
    private Map<String, StorableEvent> inMemoryCache = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public void storeEvent(StorableEvent event) {
        System.out.println("存储事件：" + event);
        inMemoryCache.put(event.getEventId(), event);
    }

    @Override
    public StorableEvent find(String eventId) {
        return inMemoryCache.get(eventId);
    }
}
