package org.ddd.event.domain;

import java.util.Set;

/**
 * @author Michael
 */
public class RetryOnSubscribeFail {
    private SubscriberHolder subscriberHolder;
    private StorableEvent storableEvent;

    public RetryOnSubscribeFail(SubscriberHolder subscriberHolder, StorableEvent event) {
        this.subscriberHolder = subscriberHolder;
        this.storableEvent = event;
    }

    public void reConsumerEvent() {
        Set<StorableSubscriber> notHandleSubscriber = storableEvent.getNotHandleSubscriber();

        for (StorableSubscriber storableSubscriber : notHandleSubscriber) {
            DefaultExecutor.execute(() ->
                    executeSubscriber(storableSubscriber.getSubscriberType(), storableEvent.getEvent()));
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void executeSubscriber(String subscriberType, Event event) {
        SubscriberWrapper subscriber = subscriberHolder.getSubscriber(subscriberType);
        EventSubscriber eventSubscriber = subscriber.getEventSubscriber();
        eventSubscriber.handle(event);
    }

}
