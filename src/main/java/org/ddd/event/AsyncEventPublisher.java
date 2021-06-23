package org.ddd.event;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Michael
 */
public class AsyncEventPublisher implements EventPublisher {
    private BlockingQueue<Event> queue;
    private EventSubscriberRegister subscriberRegister;
    private EventStorage eventStorage;
    private TransactionListener listener;

    private long timeout = 5000;

    private volatile boolean isStarted;

    public AsyncEventPublisher(BlockingQueue<Event> queue,
                               EventSubscriberRegister subscriberRegister,
                               EventStorage eventStorage,
                               TransactionListener listener) {
        Objects.requireNonNull(queue,"BlockingQueue can not null");
        Objects.requireNonNull(subscriberRegister,"subscriberRegister can not null");
        Objects.requireNonNull(eventStorage,"eventStorage can not null");
        Objects.requireNonNull(listener,"listener can not null");

        this.queue = queue;
        this.subscriberRegister = subscriberRegister;
        this.eventStorage = eventStorage;
        this.listener = listener;
    }

    @Override
    public void publishEvent(Event event) {
        if (!isStarted){
            start();
        }
        listener.afterCommit(() -> {
            try {
                queue.offer(event,timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void start() {
        if (!isStarted){
            new Thread(new EventTask(), "AsyncEventPublisher-Thread").start();
            isStarted = true;
        }
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    private class EventTask implements Runnable{

        @Override
        public void run() {
            try {
                publishEvent();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void publishEvent() throws InterruptedException {
            final Event event = queue.take();
            final Set<EventSubscriber> subscribers = getSubscribers(event.eventType());
            retryOnFail(event,subscribers, new AtomicInteger(0));
        }

        private Set<EventSubscriber> getSubscribers(String eventType) {
            return subscriberRegister.getSubscribers(eventType);
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private void retryOnFail(Event event, Set<EventSubscriber> subscribers, AtomicInteger failTimes) {
            try {
                final List<CompletableFuture<Void>> futures = subscribers.stream()
                        .map(subscriber -> CompletableFuture
                                .runAsync(() -> subscriber.handle(event), DefaultExecutor.getExecutor()))
                        .collect(Collectors.toList());

                futures.forEach(CompletableFuture::join);
                eventStorage.executeSuccess(event);
            }catch (Exception e){
                final int failTime = failTimes.incrementAndGet();
                if (failTime >= 5){
                    eventStorage.executeFail(event);
                    return;
                }
                retryOnFail(event, subscribers, failTimes);
            }
        }


    }
}
