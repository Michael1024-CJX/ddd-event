package org.ddd.event;

/**
 * 具有事件存储功能的EventPublisher
 *
 * @author Michael
 */
public class StorableEventPublisher implements EventPublisher {
    private EventStorage eventStorage;
    private EventPublisher eventPublisher;

    public StorableEventPublisher(EventStorage eventStorage, EventPublisher eventPublisher) {
        this.eventStorage = eventStorage;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishEvent(Event event) {
        storeEvent(event);
        eventPublisher.publishEvent(event);
    }

    private void storeEvent(Event event) {
        eventStorage.storeEvent(event);
    }
}
