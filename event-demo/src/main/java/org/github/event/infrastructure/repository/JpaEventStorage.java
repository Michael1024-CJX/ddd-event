package org.github.event.infrastructure.repository;

import org.ddd.event.Event;
import org.ddd.event.EventLog;
import org.ddd.event.EventStorage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjx
 */
public class JpaEventStorage implements EventStorage {
    @Override
    public void storeEvent(Event event) {

    }

    @Override
    public Event find(String eventId) {
        return null;
    }

    @Override
    public void removeEvent(Event event) {

    }

    @Override
    public List<Event> findAll() {
        return null;
    }

    @Override
    public void saveLog(EventLog log) {

    }

    @Override
    public boolean existLog(String eventId, String subscriberType) {
        return false;
    }
}

