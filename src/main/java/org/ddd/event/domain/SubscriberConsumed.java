package org.ddd.event.domain;

/**
 * @author Michael
 */
public class SubscriberConsumed implements TransactionCallback{
    private final EventStore eventStore;
    private SubscriberId subscriberId;

    public SubscriberConsumed(EventStore eventStore, SubscriberId subscriberId) {
        this.eventStore = eventStore;
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
        return eventStore.find(subscriberId.eventId());
    }

    private void storeEvent(StorableEvent storableEvent) {
        eventStore.storeEvent(storableEvent);
    }
}
