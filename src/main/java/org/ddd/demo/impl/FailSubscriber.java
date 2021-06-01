package org.ddd.demo.impl;

import org.ddd.event.domain.EventSubscriber;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michael
 * @date 2021/5/29 16:32
 */
@Component
public class FailSubscriber implements EventSubscriber<FailEvent> {
    private int count = 0;
    @Override
    @Transactional
    public void handle(FailEvent event) {
        count ++;
        System.out.println("FailSubscriber执行事件:" + event.getEventId());
        if (count < 2){
            throw new RuntimeException("FailSubscriber 处理事件失败");
        }
    }

    @Override
    public String toString() {
        return "TestSubscriber";
    }
}
