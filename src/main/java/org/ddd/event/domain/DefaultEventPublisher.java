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
        Set<SubscriberWrapper> subscriber = subscriberHolder.getSubscriber(event);
        StorableEvent storableEvent = new StorableEvent(event);
        subscriber.forEach(storableEvent::addSubscriber);
        eventStore.storeEvent(storableEvent);

        EventMulticaster multicaster = new EventMulticaster(event, subscriber);
        transactionListener.afterCommit(multicaster);
    }
}
