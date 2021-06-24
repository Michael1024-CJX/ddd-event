package org.ddd.event;

import java.util.List;

/**
 * @author Michael
 */
public interface EventStorage {
    void storeEvent(Event event);

    Event find(String eventId);

    void removeEvent(Event event);

    List<Event> findAll();

    void saveLog(EventLog log);

    boolean existLog(String eventId, String subscriberType);
}
