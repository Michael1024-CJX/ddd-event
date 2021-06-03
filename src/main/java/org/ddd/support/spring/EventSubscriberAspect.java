package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ddd.event.domain.*;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 */
@Aspect
@Component
@Slf4j
public class EventSubscriberAspect {
    private final EventStore eventStore;

    public EventSubscriberAspect(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Pointcut(value = "target(org.ddd.event.domain.EventSubscriber)")
    public void interfacePointCut() {
    }

    @After("interfacePointCut()")
    public void after(JoinPoint joinPoint) {
        afterDo(joinPoint);
    }

    private void afterDo(JoinPoint joinPoint) {
        final SubscriberId subscriberId = getSubscriberId(joinPoint);
        final SubscriberConsumed subscriberConsumed = new SubscriberConsumed(eventStore, subscriberId);
        registerSubscriberConsumedCallback(subscriberConsumed);
    }

    private void registerSubscriberConsumedCallback(SubscriberConsumed subscriberConsumed) {
        SpringTransactionManager.registerBeforeCommitCallback(subscriberConsumed);
    }

    private SubscriberId getSubscriberId(JoinPoint joinPoint) {
        String eventId = getEventId(joinPoint);
        String subscriberType = getSubscriberType(joinPoint);
        return new SubscriberId(eventId, subscriberType);
    }

    private String getEventId(JoinPoint joinPoint) {
        Event event = getEvent(joinPoint.getArgs());
        return event.getEventId();
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

    private String getSubscriberType(JoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();
        return target.getClass().getTypeName();
    }
}
