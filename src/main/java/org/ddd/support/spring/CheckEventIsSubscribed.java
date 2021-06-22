package org.ddd.support.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ddd.event.Event;
import org.ddd.event.EventStorage;

/**
 * @author chenjx
 */
public class CheckEventIsSubscribed implements MethodInterceptor {

    private EventStorage eventStorage;

    public CheckEventIsSubscribed(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Object[] arguments = invocation.getArguments();
        final Event eventArg = getEventArg(arguments);
        if (eventArg == null){
            return invocation.proceed();
        }
        if (eventStorage.existLog(eventArg.getEventId())) {
            return new Object();
        }
        return invocation.proceed();
    }

    private Event getEventArg(Object[] args){
        if (args == null || args.length == 0) {
            return null;
        }
        for (Object arg : args) {
            if (arg instanceof Event){
                return (Event) arg;
            }
        }
        return null;
    }

}
