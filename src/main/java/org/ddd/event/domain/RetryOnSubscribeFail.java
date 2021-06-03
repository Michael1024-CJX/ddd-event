package org.ddd.event.domain;

import java.util.Set;
import java.util.stream.Collectors;

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

    public void retry() {
        Set<SubscriberWrapper> needReExecutedSubscriber = getNotDoneSubscriber();
        retryMulticast(needReExecutedSubscriber);
    }

    private Set<SubscriberWrapper> getNotDoneSubscriber() {
        final Set<StorableSubscriber> notHandleSubscriber = storableEvent.getNotHandleSubscriber();
        return notHandleSubscriber
                .stream()
                .map(StorableSubscriber::getSubscriberId)
                .map(SubscriberId::subscriberType)
                .map(this::getSubscriberWrapper)
                .collect(Collectors.toSet());
    }

    private SubscriberWrapper getSubscriberWrapper(String subscriberType) {
        return subscriberHolder.getSubscriber(subscriberType);
    }

    private void retryMulticast(Set<SubscriberWrapper> needReExecutedSubscriber) {
        final EventMulticaster multicaster = new EventMulticaster(storableEvent.getEvent(), needReExecutedSubscriber);
        multicaster.multicastEvent();
    }

}
