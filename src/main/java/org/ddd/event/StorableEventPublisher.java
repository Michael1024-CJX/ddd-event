package org.ddd.event;

/**
 * @author chenjx
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
