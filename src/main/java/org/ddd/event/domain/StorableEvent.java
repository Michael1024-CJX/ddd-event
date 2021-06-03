package org.ddd.event.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
    private Set<StorableSubscriber> subscribers;
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
    }

    void addSubscriber(SubscriberWrapper eventSubscriber) {
        if (subscribers == null) {
            subscribers = new HashSet<>();
        }
        StorableSubscriber storableSubscriber = new StorableSubscriber(getEventId(),
                eventSubscriber.getSubscriberType());
        subscribers.add(storableSubscriber);
    }

    void subscriberConsumed(String subscriberType) {
        if (subscribers == null) {
            return;
        }
        for (StorableSubscriber subscriber : subscribers) {
            if (subscriberType.equals(subscriber.getSubscriberType())) {
                subscriber.consume();
            }
        }
        addConsumerNumber();
        changeStatus();
    }

    Set<StorableSubscriber> getNotHandleSubscriber() {
        return Collections.unmodifiableSet(
                subscribers
                        .stream()
                        .filter(storableSubscriber -> !storableSubscriber.isConsumed())
                        .collect(Collectors.toSet())
        );
    }

    public boolean isRunning() {
        return this.status == EventStatus.RUNNING;
    }

    public boolean isFinished() {
        return this.status == EventStatus.FINISHED;
    }

    public boolean isFailed() {
        return this.status == EventStatus.FAILED;
    }

    public String getEventId() {
        return event.getEventId();
    }

    private void addConsumerNumber() {
        numOfConsumer++;
    }

    private void changeStatus() {
        if (subscribers == null || subscribers.stream().allMatch(StorableSubscriber::isConsumed)) {
            this.status = EventStatus.FINISHED;
            return;
        }

        if (this.numOfConsumer >= MAX_FAIL_TIMES) {
            this.status = EventStatus.FAILED;
            return;
        }

        this.status = EventStatus.RUNNING;
    }

    public enum EventStatus {
        RUNNING,
        FAILED,
        FINISHED;
    }

    // JPA Entity Id
    private Long id;
}
