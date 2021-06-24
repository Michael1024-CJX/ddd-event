package org.ddd.event;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 同步事件发送器，会在同一个线程内将事件发送并执行监听者
 * 注意：由于是同步的，所以需要再保存实体后再发送事件，
 *      否则可能出现监听者找不到实体的情况。
 *
 * @author Michael
 */
public class SyncEventPublisher implements EventPublisher {
    private final EventSubscriberRegister subscriberRegister;

    public SyncEventPublisher(EventSubscriberRegister subscriberRegister) {
        Objects.requireNonNull(subscriberRegister,"subscriberRegister can not null");

        this.subscriberRegister = subscriberRegister;
    }


    @Override
    public void publishEvent(Event event) {
        final Set<EventSubscriber> subscribers = getSubscribers(event.eventType());

        executeSubscribers(event, subscribers);
    }

    private Set<EventSubscriber> getSubscribers(String eventType){
        return subscriberRegister.getSubscribers(eventType);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void executeSubscribers(Event event, Set<EventSubscriber> subscribers) {
        final List<CompletableFuture<Void>> collect = subscribers
                .stream()
                .map(subscriber -> CompletableFuture.runAsync(() -> subscriber.handle(event), DefaultExecutor.getExecutor()))
                .collect(Collectors.toList());

        collect.forEach(CompletableFuture::join);
    }
}
