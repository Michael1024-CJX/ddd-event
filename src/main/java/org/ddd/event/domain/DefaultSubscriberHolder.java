package org.ddd.event.domain;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Michael
 */
public class DefaultSubscriberHolder implements SubscriberHolder {
    private final Map<String, SubscriberWrapper> subscribers = new ConcurrentHashMap<>();
    private final Map<Class<? extends Event>, Set<SubscriberWrapper>> cacheSubscribers = new ConcurrentHashMap<>();

    @Override
    public void addSubscriber(SubscriberWrapper subscriberWrapper) {
        subscribers.put(subscriberWrapper.getSubscriberType(), subscriberWrapper);
    }

    @Override
    public Set<SubscriberWrapper> getSubscriber(Event event) {
        Class<? extends Event> eventType = event.getClass();
        if (cacheSubscribers.containsKey(eventType)) {
            return cacheSubscribers.get(eventType);
        }

        Set<SubscriberWrapper> subscriberSet = subscribers.values().stream()
                .filter(subscriberWrapper -> subscriberWrapper.getEventType().equals(eventType.getTypeName()))
                .collect(Collectors.toSet());

        cacheSubscribers.putIfAbsent(eventType, subscriberSet);
        return subscriberSet;
    }

    @Override
    public SubscriberWrapper getSubscriber(String subscriberType) {
        return subscribers.get(subscriberType);
    }
}
