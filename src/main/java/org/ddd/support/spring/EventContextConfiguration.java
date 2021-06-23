package org.ddd.support.spring;

import org.ddd.event.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael
 */
@Configuration
public class EventContextConfiguration {
    @Bean
    public EventPublisher eventPublisher(EventSubscriberRegister subscriberRegister,
                                         EventStorage eventStorage) {
        return new StorableEventPublisher(eventStorage, new DelayAsyncEventPublisher(subscriberRegister,eventStorage));
    }

    @Bean
    public EventSubscriberRegister eventSubscriberRegister() {
        return new DefaultSubscriberRegister();
    }

    @Bean
    @Conditional(EventStorageCondition.class)
    public EventStorage eventStorage() {
        return new InMemoryEventStorage();
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
