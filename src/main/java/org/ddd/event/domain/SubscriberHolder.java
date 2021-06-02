package org.ddd.event.domain;

import java.util.Set;

/**
 * @Author Michael
 */
public interface SubscriberHolder {
    void addSubscriber(SubscriberWrapper subscriberWrapper);

    Set<SubscriberWrapper> getSubscriber(Event event);

    SubscriberWrapper getSubscriber(String subscriberType);
}
