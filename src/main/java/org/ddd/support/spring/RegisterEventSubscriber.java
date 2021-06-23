package org.ddd.support.spring;

import org.ddd.event.EventSubscriber;
import org.ddd.event.EventSubscriberRegister;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author chenjx
 */
@Component
public class RegisterEventSubscriber implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final EventSubscriberRegister eventSubscriberRegister = beanFactory.getBean(EventSubscriberRegister.class);
        final Map<String, EventSubscriber> subscriberMap = beanFactory.getBeansOfType(EventSubscriber.class);

        if (CollectionUtils.isEmpty(subscriberMap)) {
            subscriberMap.forEach((key, value) -> eventSubscriberRegister.registerSubscriber(AopUtils.getTargetClass(value), value));
        }
    }
}
