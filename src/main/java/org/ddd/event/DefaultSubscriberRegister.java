package org.ddd.event;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Michael
 */
public class DefaultSubscriberRegister implements EventSubscriberRegister {
    private final Map<String, SubscriberWrapper> subscribers = new ConcurrentHashMap<>();
    private final Map<String, Set<EventSubscriber>> cacheSubscribers = new ConcurrentHashMap<>();

    public DefaultSubscriberRegister() {
    }

    @Override
    public void registerSubscriber(Class<?> aClass, EventSubscriber eventSubscriber) {
        SubscriberWrapper subscriberWrapper = new SubscriberWrapper(aClass, eventSubscriber);
        subscribers.put(subscriberWrapper.getSubscriberType(), subscriberWrapper);
    }

    @Override
    public Set<EventSubscriber> getSubscribers(String eventType) {
        if (isCached(eventType)) {
            return getFromCache(eventType);
        }
        final Set<EventSubscriber> subscriberWrappers = searchSubscriber(eventType);
        cache(eventType, subscriberWrappers);
        return subscriberWrappers;
    }

    private boolean isCached(String eventType) {
        return cacheSubscribers.containsKey(eventType);
    }

    private Set<EventSubscriber> getFromCache(String eventType) {
        final Set<EventSubscriber> subscribers = cacheSubscribers.get(eventType);
        if (subscribers == null){
            return Collections.emptySet();
        }
        return subscribers;
    }

    private Set<EventSubscriber> searchSubscriber(String eventType) {
        return subscribers
                .values()
                .stream()
                .filter(subscriberWrapper -> subscriberWrapper.getEventType().equals(eventType))
                .map(SubscriberWrapper::getEventSubscriber)
                .collect(Collectors.toSet());
    }

    private void cache(String eventType, Set<EventSubscriber> subscriberSet) {
        if (subscriberSet != null && !subscriberSet.isEmpty()){
            cacheSubscribers.putIfAbsent(eventType, subscriberSet);
        }
    }
}
