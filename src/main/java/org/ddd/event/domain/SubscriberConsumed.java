package org.ddd.event.domain;

/**
 * @author Michael
 */
public class SubscriberConsumed implements TransactionCallback{
    private final EventStorage eventStorage;
    private SubscriberId subscriberId;

    public SubscriberConsumed(EventStorage eventStorage, SubscriberId subscriberId) {
        this.eventStorage = eventStorage;
        this.subscriberId = subscriberId;
    }


    @Override
    public void callback() {
        StorableEvent storableEvent = getEvent();
        if (storableEvent == null){
            return;
        }
        storableEvent.consume(subscriberId);
        storeEvent(storableEvent);
    }

    private StorableEvent getEvent() {
        return eventStorage.find(subscriberId.eventId());
    }

    private void storeEvent(StorableEvent storableEvent) {
        eventStorage.storeEvent(storableEvent);
    }
}
