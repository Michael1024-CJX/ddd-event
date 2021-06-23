package org.ddd.event;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Michael
 */
public class SyncEventPublisher implements EventPublisher {
    private final EventSubscriberRegister subscriberRegister;
    private final EventStorage eventStorage;

    public SyncEventPublisher(EventSubscriberRegister subscriberRegister, EventStorage eventStorage) {
        Objects.requireNonNull(subscriberRegister,"subscriberRegister can not null");
        Objects.requireNonNull(eventStorage,"eventStorage can not null");

        this.subscriberRegister = subscriberRegister;
        this.eventStorage = eventStorage;
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
        eventStorage.executeSuccess(event);
    }
}
