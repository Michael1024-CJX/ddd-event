package org.ddd.event;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 使用BlockingQueue来发送事件
 * 依赖事务回调机制，待事务执行成功后将事件发送到阻塞队列中，
 * 通过BlockingQueue.offer()方法存储事件，并设置超时时间，
 * 超时后将自动放弃事件分发，
 * 如果使用了StorableEventPublisher存储事件，则可以后续手动重发事件。
 * 等待中的事件顺序由BlockingQueue保证，若是公平锁则能保证顺序，否则将不保证。
 *
 * 并有一个异步线程循环调用BlockingQueue.take()方法获取事件
 * 改方法在Queue中不存在元素的时候会自动阻塞
 *
 * 为了防止部分监听者执行时间过长，故加入TimeoutFail策略
 *
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
        Objects.requireNonNull(queue, "BlockingQueue can not null");
        Objects.requireNonNull(subscriberRegister, "subscriberRegister can not null");
        Objects.requireNonNull(eventStorage, "eventStorage can not null");
        Objects.requireNonNull(listener, "listener can not null");

        this.queue = queue;
        this.subscriberRegister = subscriberRegister;
        this.eventStorage = eventStorage;
        this.listener = listener;
    }

    @Override
    public void publishEvent(Event event) {
        if (!isStarted) {
            start();
        }
        listener.afterCommit(() -> {
            try {
                queue.offer(event, timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void start() {
        if (!isStarted) {
            new Thread(new EventTask(), "AsyncEventPublisher-Thread").start();
            isStarted = true;
        }
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    private class EventTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    publishEvent();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void publishEvent() throws InterruptedException {
            final Event event = queue.take();
            final Set<EventSubscriber> subscribers = getSubscribers(event.eventType());
            retryOnFail(event, subscribers, new AtomicInteger(0));
        }

        private Set<EventSubscriber> getSubscribers(String eventType) {
            return subscriberRegister.getSubscribers(eventType);
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private void retryOnFail(Event event, Set<EventSubscriber> subscribers, AtomicInteger failTimes) {
            try {
                TimeoutFail timeoutFail = new TimeoutFail(1000, TimeUnit.MILLISECONDS);
                final List<CompletableFuture<Void>> futures = subscribers.stream()
                        .map(subscriber -> {
                            final CompletableFuture<Void> future = CompletableFuture.runAsync(
                                    () -> subscriber.handle(event), DefaultExecutor.getExecutor());
                            return timeoutFail.fastFail(future);
                        })
                        .collect(Collectors.toList());

                futures.forEach(CompletableFuture::join);
                eventStorage.executeSuccess(event);
            } catch (Exception e) {
                final int failTime = failTimes.incrementAndGet();
                if (failTime >= 5) {
                    eventStorage.executeFail(event);
                    return;
                }
                retryOnFail(event, subscribers, failTimes);
            }
        }


    }
}
