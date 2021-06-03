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
        if (isCache(event)) {
            return getFromCache(event);
        }
        final Set<SubscriberWrapper> subscriberWrappers = searchSubscriber(event);
        cache(event, subscriberWrappers);
        return subscriberWrappers;
    }

    private boolean isCache(Event event) {
        final String eventType = getEventType(event);
        return cacheSubscribers.containsKey(eventType);
    }

    private Set<SubscriberWrapper> getFromCache(Event event) {
        final String eventType = getEventType(event);
        return cacheSubscribers.get(eventType);
    }

    private Set<SubscriberWrapper> searchSubscriber(Event event) {
        final String eventType = getEventType(event);

        return getSubscriberByEvenType(eventType);
    }

    private Set<SubscriberWrapper> getSubscriberByEvenType(String eventType) {
        return subscribers
                .values()
                .stream()
                .filter(subscriberWrapper -> subscriberWrapper.getEventType().equals(eventType))
                .collect(Collectors.toSet());
    }

    private void cache(Event event, Set<SubscriberWrapper> subscriberSet) {
        if (subscriberSet != null && !subscriberSet.isEmpty()){
            final String eventType = getEventType(event);
            cacheSubscribers.putIfAbsent(eventType, subscriberSet);
        }
    }

    private String getEventType(Event event) {
        final Class<? extends Event> aClass = event.getClass();
        return aClass.getTypeName();
    }

    @Override
    public SubscriberWrapper getSubscriber(String subscriberType) {
        return subscribers.get(subscriberType);
    }
}
