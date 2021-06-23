package org.ddd.event;

import java.util.List;

/**
 * @author Michael
 */
public interface EventStorage {
    void storeEvent(Event event);

    Event find(String eventId);

    List<Event> findNotFinishEvent();

    void executeFail(Event event);

    void executeSuccess(Event event);

    void saveLog(EventLog log);

    boolean existLog(String eventId, String subscriberType);
}
