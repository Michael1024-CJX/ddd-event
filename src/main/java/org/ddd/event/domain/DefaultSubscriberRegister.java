package org.ddd.event.domain;

/**
 * @author Michael
 */
public class DefaultSubscriberRegister implements EventSubscriberRegister {
    private SubscriberHolder subscriberHolder;

    public DefaultSubscriberRegister(SubscriberHolder subscriberHolder) {
        this.subscriberHolder = subscriberHolder;
    }

    @Override
    public void registerSubscriber(Class<?> aClass, EventSubscriber eventSubscriber) {
        SubscriberWrapper subscriberWrapper = new SubscriberWrapper(aClass, eventSubscriber);

        subscriberHolder.addSubscriber(subscriberWrapper);
    }
}
