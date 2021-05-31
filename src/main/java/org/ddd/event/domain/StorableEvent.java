package org.ddd.event.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael
 * @date 2021/5/30 19:06
 */
public class StorableEvent {
    private Event event;
    private boolean finished = true;
    private Set<StorableSubscriber> subscribers;

    public StorableEvent(Event event) {
        this.event = event;
    }

    public void addSubscriber(SubscriberWrapper eventSubscriber) {
        if (subscribers == null) {
            subscribers = new HashSet<>();
        }
        StorableSubscriber storableSubscriber = new StorableSubscriber(getEventId(),
                eventSubscriber.getSubscriberType());
        subscribers.add(storableSubscriber);
        finished = false;
    }

    public void subscriberConsumed(String subscriberType) {
        if (subscribers == null) {
            return;
        }
        for (StorableSubscriber subscriber : subscribers) {
            if (subscriberType.equals(subscriber.getSubscriberType())) {
                subscriber.consume();
            }
        }
        checkIsFinished();
    }

    private void checkIsFinished() {
        if (subscribers == null) {
            finished = true;
            return;
        }
        if (subscribers.stream().allMatch(StorableSubscriber::isConsumed)) {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public String getEventId(){
        return event.getEventId();
    }

    @Override
    public String toString() {
        return "StorableEvent{" +
                "event=" + event +
                ", finished=" + finished +
                ", subscribers=" + subscribers +
                '}';
    }
}
