package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.ddd.event.Event;
import org.ddd.event.EventLog;
import org.ddd.event.EventStorage;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 */
@Aspect
@Component
@Slf4j
public class EventAspect {
    static final String EXPRESSION = "target(org.ddd.event.EventSubscriber)";
    private final EventStorage eventStorage;

    public EventAspect(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    @Pointcut(value = EXPRESSION)
    public void interfacePointCut() {
    }

    @After("interfacePointCut()")
    public void after(JoinPoint joinPoint) {
        final String subscriberType = getSubscriberType(joinPoint);

        final Object[] args = joinPoint.getArgs();
        final Event event = getEvent(args);
        final EventLog eventLog = new EventLog();
        eventLog.setEventId(event.getEventId());
        eventLog.setEventType(event.eventType());
        eventLog.setSubscriberType(subscriberType);
        eventStorage.saveLog(eventLog);
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
