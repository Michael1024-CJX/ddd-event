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
 * @date 2021/5/30 19:06
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

    public StorableEvent(Event event) {
        this.event = event;
        this.status = EventStatus.RUNNING;
    }

    public void addSubscriber(SubscriberWrapper eventSubscriber) {
        if (subscribers == null) {
            subscribers = new HashSet<>();
        }
        StorableSubscriber storableSubscriber = new StorableSubscriber(getEventId(),
                eventSubscriber.getSubscriberType());
        subscribers.add(storableSubscriber);
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
        addConsumerNumber();
        changeStatus();
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

    public Set<StorableSubscriber> getNotHandleSubscriber() {
        return Collections.unmodifiableSet(subscribers.stream()
                .filter(storableSubscriber -> !storableSubscriber.isConsumed())
                .collect(Collectors.toSet()));
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

    public enum EventStatus {
        RUNNING,
        FAILED,
        FINISHED;
    }

    // JPA Entity Id
    private Long id;
}
