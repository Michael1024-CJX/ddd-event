package org.ddd.event.domain;

import java.util.Set;

/**
 * @Author Michael
 * @Date 2021/5/30 19:32
 */
public class EventMulticaster implements TransactionCallback {
    private Event event;
    private Set<SubscriberWrapper> eventSubscribers;

    public EventMulticaster(Event event, Set<SubscriberWrapper> eventSubscribers) {
        this.event = event;
        this.eventSubscribers = eventSubscribers;
    }

    @Override
    public void callback() {
        multicastEvent();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void multicastEvent() {
        for (SubscriberWrapper subscriber : eventSubscribers) {
            DefaultExecutor.execute(() -> subscriber.getEventSubscriber().handle(event));
        }
    }
}
