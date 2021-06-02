package org.ddd.demo.impl;

import org.ddd.event.domain.EventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author chenjx@dist.com.cn
 * @date 2021/6/2 10:24
 */
@Component
public class DemoComponent {
    private EventPublisher eventPublisher;

    public DemoComponent(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void simulateTransaction(FailEvent failEvent) {
        System.out.println("事务已开启：" + TransactionSynchronizationManager.isSynchronizationActive());
        eventPublisher.publishEvent(failEvent);
        throw new RuntimeException("模拟事务提交失败");
    }
}
