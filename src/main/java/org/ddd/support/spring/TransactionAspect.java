package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ddd.demo.impl.Run;
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
@Slf4j
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
        Event event = getEvent(joinPoint.getArgs());
        String eventId = event.getEventId();
        String subscriberType = joinPoint.getTarget().getClass().getTypeName();

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    if (readOnly) {
                        throw new IllegalStateException("read only 状态下不可修改事件的状态");
                    }
                    new SubscriberConsumed(eventStore, eventId, subscriberType).callback();
                }
            });
        } else {
            log.warn("subscriber[{}]未开启事务，可能影响event[{}]的存储", subscriberType, eventId);
            new SubscriberConsumed(eventStore, eventId, subscriberType).callback();
        }
    }

    private Event getEvent(Object[] args) {
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof Event) {
                    return (Event) arg;
                }
            }
        }
        throw new IllegalArgumentException("未获取到 event 参数");
    }
}
