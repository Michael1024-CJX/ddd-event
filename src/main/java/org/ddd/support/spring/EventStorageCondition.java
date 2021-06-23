package org.ddd.support.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author chenjx
 */
public class EventStorageCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final BeanDefinitionRegistry registry = context.getRegistry();
        return !registry.containsBeanDefinition("eventStorage");
    }
}
