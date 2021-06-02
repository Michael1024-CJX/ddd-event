package org.ddd.demo.impl;

import org.ddd.event.domain.EventSubscriber;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michael
 */
@Component
public class TestSubscriber implements EventSubscriber<TestEvent> {

    @Override
    @Transactional
    public void handle(TestEvent event) {
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=============订阅者接收事件=======" + "TestSubscriber" + " --> " + event);
        System.out.println("TestSubscriber执行事件:" + event.getEventId());
    }

    @Override
    public String toString() {
        return "TestSubscriber";
    }
}
