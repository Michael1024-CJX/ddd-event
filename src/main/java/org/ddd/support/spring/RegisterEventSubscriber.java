package org.ddd.support.spring;

import org.ddd.event.EventSubscriber;
import org.ddd.event.EventSubscriberRegister;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author Michael
 */
@Component
public class RegisterEventSubscriber implements InitializingBean {
    private final ApplicationContext applicationContext;

    public RegisterEventSubscriber(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final EventSubscriberRegister eventSubscriberRegister = applicationContext.getBean(EventSubscriberRegister.class);
        final Map<String, EventSubscriber> subscriberMap = applicationContext.getBeansOfType(EventSubscriber.class);

        if (!CollectionUtils.isEmpty(subscriberMap)) {
            subscriberMap.forEach((key, value) -> eventSubscriberRegister.registerSubscriber(AopUtils.getTargetClass(value), value));
        }
    }
}
