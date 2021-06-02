package org.ddd.event.domain;

/**
 * @author Michael
 * @date 2021/5/31 14:55
 */
public class SubscriberConsumed implements TransactionCallback{
    private final EventStore eventStore;
    private String eventId;
    private String subscriberType;

    public SubscriberConsumed(EventStore eventStore, String eventId, String subscriberType) {
        this.eventStore = eventStore;
        this.eventId = eventId;
        this.subscriberType = subscriberType;
    }


    @Override
    public void callback() {
        StorableEvent storableEvent = eventStore.find(eventId);
        if (storableEvent == null){
            return;
        }
        storableEvent.subscriberConsumed(subscriberType);
        eventStore.storeEvent(storableEvent);
    }
}
