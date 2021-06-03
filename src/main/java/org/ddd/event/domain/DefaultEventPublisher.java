package org.ddd.event.domain;

import java.util.Set;

/**
 * @author Michael
 */
public class DefaultEventPublisher implements EventPublisher {
    private SubscriberHolder subscriberHolder;
    private EventStore eventStore;
    private TransactionListener transactionListener;

    public DefaultEventPublisher(SubscriberHolder subscriberHolder,
                                 EventStore eventStore,
                                 TransactionListener transactionListener) {
        this.subscriberHolder = subscriberHolder;
        this.eventStore = eventStore;
        this.transactionListener = transactionListener;
    }

    @Override
    public void publishEvent(Event event) {
        storeEvent(event);
        multicastEvent(event);
    }

    private void storeEvent(Event event) {
        final Set<SubscriberWrapper> subscriber = getSubscriberByEvent(event);
        final StorableEvent storableEvent = constructStorableEvent(event, subscriber);
        store(storableEvent);
    }

    private StorableEvent constructStorableEvent(Event event, Set<SubscriberWrapper> subscriber) {
        final StorableEvent storableEvent = StorableEvent.newStorableEvent(event);
        storableEvent.addSubscribers(subscriber);
        return storableEvent;
    }

    private void multicastEvent(Event event) {
        final Set<SubscriberWrapper> subscriber = getSubscriberByEvent(event);
        EventMulticaster multicaster = new EventMulticaster(event, subscriber);
        monitorTransactionCommit(multicaster);
    }

    private void store(StorableEvent storableEvent) {
        eventStore.storeEvent(storableEvent);
    }

    private void monitorTransactionCommit(EventMulticaster multicaster) {
        transactionListener.afterCommit(multicaster);
    }

    private Set<SubscriberWrapper> getSubscriberByEvent(Event event) {
        return subscriberHolder.getSubscriber(event);
    }

}
