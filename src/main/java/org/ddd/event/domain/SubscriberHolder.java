package org.ddd.event.domain;

import java.util.Set;

/**
 * @Author Michael
 * @Date 2021/5/28 20:35
 */
public interface SubscriberHolder {
    void addSubscriber(SubscriberWrapper subscriberWrapper);

    Set<SubscriberWrapper> getSubscriber(Event event);

    SubscriberWrapper getSubscriber(String subscriberType);
}
