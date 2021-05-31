package org.ddd.demo.impl;

import org.ddd.event.domain.EventSubscriber;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michael
 * @date 2021/5/29 16:32
 */
@Component
public class TestSubscriber implements EventSubscriber<TestEvent> {

    @Override
    @Transactional
    public void handle(TestEvent event) {
        System.out.println("TestSubscriber执行事件:" + event.getEventId());
    }

    @Override
    public String toString() {
        return "TestSubscriber";
    }
}
