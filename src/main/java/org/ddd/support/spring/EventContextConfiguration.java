package org.ddd.support.spring;

import org.ddd.event.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Michael
 */
@Configuration
public class EventContextConfiguration {
    @Bean
    public EventPublisher eventPublisher(EventSubscriberRegister subscriberRegister,
                                         EventStorage eventStorage,
                                         TransactionListener listener) {
        return new StorableEventPublisher(eventStorage,
                new AsyncEventPublisher(
                        new LinkedBlockingQueue<>(500),
                        subscriberRegister,
                        listener));
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

    @Bean
    public SpringTransactionListener springTransactionListener(){
        final SpringTransactionManager transactionManager = new SpringTransactionManager();
        return new SpringTransactionListener(transactionManager);
    }
}
