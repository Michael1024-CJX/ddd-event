package org.ddd.event.domain;

import org.ddd.util.GenericSuperclassUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author Michael
 * @date 2021/5/28 20:40
 */
public class DefaultSubscriberHolder implements SubscriberHolder {
    private final Set<SubscriberWrapper> subscribers = new CopyOnWriteArraySet<>();
    private final Map<Class<? extends Event>, Set<SubscriberWrapper>> cacheSubscribers = new ConcurrentHashMap<>();

    @Override
    public void addSubscriber(SubscriberWrapper subscriberWrapper) {
        subscribers.add(subscriberWrapper);
    }

    @Override
    public Set<SubscriberWrapper> getSubscriber(Event event) {
        Class<? extends Event> eventType = event.getClass();
        if (cacheSubscribers.containsKey(eventType)) {
            return cacheSubscribers.get(eventType);
        }

        Set<SubscriberWrapper> subscriberSet = subscribers.stream()
                .filter(subscriberWrapper -> subscriberWrapper.getEventType().equals(eventType.getTypeName()))
                .collect(Collectors.toSet());

        cacheSubscribers.putIfAbsent(eventType, subscriberSet);
        return subscriberSet;
    }
}
