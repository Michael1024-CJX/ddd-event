package org.ddd.support.spring;

import org.ddd.event.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Michael
 */
@Configuration
public class EventContextConfiguration {
    @Bean
    public EventPublisher eventPublisher(EventSubscriberRegister subscriberRegister,
                                         EventStorage eventStorage,
                                         TransactionListener transactionListener) {
        return new StorableEventPublisher(eventStorage, new SyncEventPublisher(subscriberRegister,eventStorage));
    }

    @Bean
    public EventSubscriberRegister eventHandlerRegister(List<EventSubscriber> eventSubscribers) {
        DefaultSubscriberRegister subscriberRegister = new DefaultSubscriberRegister();
        for (EventSubscriber eventSubscriber : eventSubscribers) {
            Class<?> targetClass = AopUtils.getTargetClass(eventSubscriber);
            subscriberRegister.registerSubscriber(targetClass, eventSubscriber);
        }
        return subscriberRegister;
    }

    @Bean
    public EventStorage eventStorage() {
        return null;
    }

    @Bean
    public DefaultPointcutAdvisor checkEventIsSubscribed(EventStorage eventStorage) {
        CheckEventIsSubscribed interceptor = new CheckEventIsSubscribed(eventStorage);

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(EventAspect.EXPRESSION);

        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}
