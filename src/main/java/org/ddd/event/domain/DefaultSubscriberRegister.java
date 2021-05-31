package org.ddd.event.domain;

/**
 * @author Michael
 * @date 2021/5/28 19:59
 */
public class DefaultSubscriberRegister implements EventSubscriberRegister {
    private SubscriberHolder subscriberHolder;

    public DefaultSubscriberRegister(SubscriberHolder subscriberHolder) {
        this.subscriberHolder = subscriberHolder;
    }

    @Override
    public void registerSubscriber(Class<?> aClass, EventSubscriber eventSubscriber) {
        SubscriberWrapper subscriberWrapper = new SubscriberWrapper(aClass, eventSubscriber);
        //Stream.of(testHandler.getClass().getMethods())
        // .filter(method -> method.getName().equals("handle"))
        // .map(method -> method.getParameters())
        // .collect(Collectors.toList())
        subscriberHolder.addSubscriber(subscriberWrapper);
    }
}
