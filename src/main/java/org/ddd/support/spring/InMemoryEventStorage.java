package org.ddd.support.spring;

import org.ddd.event.Event;
import org.ddd.event.EventLog;
import org.ddd.event.EventStorage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjx
 */
public class InMemoryEventStorage implements EventStorage {
    private Map<String, Event> eventMap = new ConcurrentHashMap<>();
    private Map<String, Map<String,EventLog>> eventLogMap = new ConcurrentHashMap<>();

    @Override
    public void storeEvent(Event event) {
        eventMap.put(event.getEventId(), event);
    }

    @Override
    public Event find(String eventId) {
        return eventMap.get(eventId);
    }

    @Override
    public List<Event> findNotFinishEvent() {
        return new ArrayList<>(eventMap.values());
    }

    @Override
    public void executeFail(Event event) {
        eventMap.remove(event.getEventId());
    }

    @Override
    public void executeSuccess(Event event) {
        eventMap.remove(event.getEventId());
    }

    @Override
    public void saveLog(EventLog log) {
        final Map<String, EventLog> eventLogMap = this.eventLogMap.computeIfAbsent(log.getEventId(), k -> new HashMap<>());
        eventLogMap.put(log.getSubscriberType(), log);
    }

    @Override
    public boolean existLog(String eventId, String subscriberType) {
        if (eventLogMap.containsKey(eventId)) {
            final Map<String, EventLog> eventLogMap = this.eventLogMap.get(eventId);
            return eventLogMap.containsKey(subscriberType);
        }
        return false;
    }
}
