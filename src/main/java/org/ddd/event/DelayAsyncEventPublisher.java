package org.ddd.event;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Michael
 */
public class DelayAsyncEventPublisher implements EventPublisher {
    private  ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(
            1,
            r -> new Thread("DelayAsyncEventPublisher-Thread"));
    private static final int TASK_DELAY = 3000;

    private EventSubscriberRegister subscriberRegister;
    private EventStorage eventStorage;

    private volatile boolean isStarted = false;

    public DelayAsyncEventPublisher(EventSubscriberRegister subscriberRegister, EventStorage eventStorage) {
        Objects.requireNonNull(subscriberRegister,"subscriberRegister can not null");
        Objects.requireNonNull(eventStorage,"eventStorage can not null");

        this.subscriberRegister = subscriberRegister;
        this.eventStorage = eventStorage;
    }

    @Override
    public void publishEvent(Event event) {
        if (!isStarted) {
            start();
        }
    }

    private synchronized void start() {
        if (!isStarted) {
            executorService.scheduleWithFixedDelay(
                    new PeriodicallyPublishEvent(),
                    TASK_DELAY,
                    TASK_DELAY,
                    TimeUnit.MILLISECONDS);
            isStarted = true;
        }
    }

    private class PeriodicallyPublishEvent implements Runnable {

        @Override
        public void run() {
            final List<Event> events = getEvents();

            for (Event event : events) {
                final Set<EventSubscriber> subscribers = getSubscribers(event.eventType());
                executeSubscribers(event, subscribers);
            }
        }

        private List<Event> getEvents() {
            final List<Event> notFinishEvent = eventStorage.findNotFinishEvent();
            if (notFinishEvent == null || notFinishEvent.isEmpty()) {
                return Collections.emptyList();
            }
            return notFinishEvent;
        }

        private Set<EventSubscriber> getSubscribers(String eventType) {
            return subscriberRegister.getSubscribers(eventType);
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private void executeSubscribers(Event event, Set<EventSubscriber> subscribers) {
            final List<CompletableFuture<Void>> futures = subscribers.stream()
                    .map(subscriber -> CompletableFuture.runAsync(() -> subscriber.handle(event), DefaultExecutor.getExecutor()))
                    .collect(Collectors.toList());

            for (CompletableFuture<Void> future : futures) {
                try {
                    future.get();
                    eventStorage.executeSuccess(event);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    eventStorage.executeFail(event);
                }
            }
        }



    }
}
