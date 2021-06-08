package org.ddd.support.spring;

import org.ddd.event.domain.*;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Michael
 */
@Configuration
public class EventContextConfiguration {
    @Bean
    public SubscriberHolder handlerHolder() {
        return new DefaultSubscriberHolder();
    }

    @Bean
    public EventPublisher eventPublisher(SubscriberHolder subscriberHolder,
                                         EventStorage eventStorage,
                                         TransactionListener transactionListener) {
        return new DefaultEventPublisher(subscriberHolder, eventStorage, transactionListener);
    }

    @Bean
    public EventSubscriberRegister eventHandlerRegister(SubscriberHolder subscriberHolder, List<EventSubscriber> eventSubscribers) {
        DefaultSubscriberRegister subscriberRegister = new DefaultSubscriberRegister(subscriberHolder);
        for (EventSubscriber eventSubscriber : eventSubscribers) {
            Class<?> targetClass = AopUtils.getTargetClass(eventSubscriber);
            subscriberRegister.registerSubscriber(targetClass, eventSubscriber);
        }
        return subscriberRegister;
    }
}
