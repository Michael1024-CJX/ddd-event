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
    private final Map<String, Set<SubscriberWrapper>> cacheSubscribers = new ConcurrentHashMap<>();

    @Override
    public void addSubscriber(SubscriberWrapper subscriberWrapper) {
        subscribers.put(subscriberWrapper.getSubscriberType(), subscriberWrapper);
    }

    @Override
    public Set<SubscriberWrapper> getSubscriber(Event event) {
        String eventType = event.eventType();
        if (isCached(eventType)) {
            return getFromCache(eventType);
        }
        final Set<SubscriberWrapper> subscriberWrappers = searchSubscriber(eventType);
        cache(eventType, subscriberWrappers);
        return subscriberWrappers;
    }

    private boolean isCached(String eventType) {
        return cacheSubscribers.containsKey(eventType);
    }

    private Set<SubscriberWrapper> getFromCache(String eventType) {
        return cacheSubscribers.get(eventType);
    }

    private Set<SubscriberWrapper> searchSubscriber(String eventType) {
        return subscribers
                .values()
                .stream()
                .filter(subscriberWrapper -> subscriberWrapper.getEventType().equals(eventType))
                .collect(Collectors.toSet());
    }

    private void cache(String eventType, Set<SubscriberWrapper> subscriberSet) {
        if (subscriberSet != null && !subscriberSet.isEmpty()){
            cacheSubscribers.putIfAbsent(eventType, subscriberSet);
        }
    }

    @Override
    public SubscriberWrapper getSubscriber(String subscriberType) {
        return subscribers.get(subscriberType);
    }
}
