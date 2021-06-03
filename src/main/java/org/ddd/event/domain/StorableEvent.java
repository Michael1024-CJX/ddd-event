package org.ddd.event.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Michael
 */
@Setter
@Getter
@ToString
public class StorableEvent {
    private final static int MAX_FAIL_TIMES = 5;
    private Event event;
    private EventStatus status;
    private Map<SubscriberId, StorableSubscriber> subscribers;
    private int numOfConsumer;

    public static StorableEvent newStorableEvent(Event event) {
        return new StorableEvent(event);
    }

    public static StorableEvent newStorableEvent(Event event, Set<SubscriberWrapper> eventSubscribers) {
        final StorableEvent storableEvent = new StorableEvent(event);
        eventSubscribers.forEach(storableEvent::addSubscriber);
        return storableEvent;
    }

    private StorableEvent(Event event) {
        this.event = event;
        this.status = EventStatus.RUNNING;
        this.subscribers = new HashMap<>();
    }

    public void addSubscriber(SubscriberWrapper eventSubscriber) {
        StorableSubscriber storableSubscriber = new StorableSubscriber(getEventId(), eventSubscriber.getSubscriberType());
        addSubscriber(storableSubscriber);
    }

    public String getEventId() {
        return event.getEventId();
    }

    private void addSubscriber(StorableSubscriber storableSubscriber) {
        subscribers.put(storableSubscriber.getSubscriberId(), storableSubscriber);
    }

    public void consume(SubscriberId subscriberId) {
        if (noSubscribers()) {
            return;
        }
        final StorableSubscriber storableSubscriber = getStorableSubscriber(subscriberId);
        storableSubscriber.consume();
        increaseNumOfConsume();
        changeStatus();
    }

    private StorableSubscriber getStorableSubscriber(SubscriberId subscriberId) {
        return subscribers.get(subscriberId);
    }

    private void increaseNumOfConsume() {
        numOfConsumer++;
    }

    private void changeStatus() {
        if (isFinished()) {
            this.status = EventStatus.FINISHED;
            return;
        }

        if (isFailed()) {
            this.status = EventStatus.FAILED;
            return;
        }

        this.status = EventStatus.RUNNING;
    }

    public Set<StorableSubscriber> getNotHandleSubscriber() {
        return Collections.unmodifiableSet(
                subscribers.values().stream()
                        .filter(storableSubscriber -> !storableSubscriber.isConsumed())
                        .collect(Collectors.toSet())
        );
    }

    public boolean isRunning() {
        return !noSubscribers() && numOfConsumeIsLegal() && !allSubscriberIsConsumed();
    }

    public boolean isFinished() {
        return noSubscribers() ||
                (numOfConsumeIsLegal() && allSubscriberIsConsumed());
    }

    public boolean isFailed() {
        return !numOfConsumeIsLegal() && !allSubscriberIsConsumed();
    }

    private boolean allSubscriberIsConsumed() {
        return subscribers.values().stream().allMatch(StorableSubscriber::isConsumed);
    }

    private boolean numOfConsumeIsLegal() {
        return this.numOfConsumer < MAX_FAIL_TIMES;
    }

    private boolean noSubscribers() {
        return subscribers == null || subscribers.isEmpty();
    }

    public enum EventStatus {
        RUNNING,
        FAILED,
        FINISHED;
    }

    // JPA Entity Id
    private Long id;
}
