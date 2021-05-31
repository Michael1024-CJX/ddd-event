package org.ddd.demo.impl;


import org.ddd.event.domain.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michael
 * @date 2021/5/29 16:34
 */
@Component
public class Run implements CommandLineRunner {
    @Autowired
    private EventPublisher publisher;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        TestEvent testEvent = new TestEvent(this);
        publisher.publishEvent(testEvent);
        System.out.println("=====事务准备提交======");
        Thread.sleep(1000);
    }
}
