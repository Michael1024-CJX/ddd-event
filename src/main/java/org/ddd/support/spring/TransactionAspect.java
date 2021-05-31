package org.ddd.support.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ddd.event.domain.Event;
import org.ddd.event.domain.EventStore;
import org.ddd.event.domain.SubscriberConsumed;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Michael
 * @date 2021/5/31 13:16
 */
@Aspect
@Component
public class TransactionAspect {
    private final EventStore eventStore;

    public TransactionAspect(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Pointcut(value = "target(org.ddd.event.domain.EventSubscriber)")
    public void interfacePointCut() {
    }

    @After("interfacePointCut()")
    public void after(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String subscriberType = joinPoint.getTarget().getClass().getName();
        Event event;
        if (args != null && args.length > 0 && args[0] instanceof Event){
            event = (Event) args[0];
        }else {
            return;
        }
        String eventId = event.getEventId();
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            System.out.println("============= 已开启事务 ==============");
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    if (!readOnly){
                        new SubscriberConsumed(eventStore, eventId, subscriberType).callback();
                    }
                }
            });
        }else {
            System.out.println("============= 未开启事务 ==============");
            new SubscriberConsumed(eventStore, eventId, subscriberType).callback();
        }
    }
}
