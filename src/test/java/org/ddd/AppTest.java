package org.ddd;

import static org.junit.Assert.assertTrue;

import org.ddd.demo.impl.DemoComponent;
import org.ddd.demo.impl.FailEvent;
import org.ddd.demo.impl.TestEvent;
import org.ddd.event.EventPublisher;
import org.ddd.event.EventStorage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
    @Autowired
    private EventPublisher publisher;
    @Autowired
    private EventStorage eventStorage;
    @Autowired
    private DemoComponent demoComponent;

    @Test
    @Transactional
    public void publishEventTest() throws Exception {
        TestEvent testEvent = new TestEvent(this);
        publisher.publishEvent(testEvent);
        StorableEvent testStorableEvent = eventStorage.find(testEvent.getEventId());

        Assert.assertNotNull("testEvent success publish", testStorableEvent);
    }

    @Test
    public void publishEventNoTXTest() throws Exception {
        TestEvent testEvent = new TestEvent(this);
        publisher.publishEvent(testEvent);
        StorableEvent testStorableEvent = eventStorage.find(testEvent.getEventId());

        Assert.assertNotNull("testEvent success publish", testStorableEvent);
    }

    @Test
    public void publishEventFailedTest() throws Exception {
        FailEvent failEvent = new FailEvent(this);

        try {
            demoComponent.simulateTransaction(failEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StorableEvent testStorableEvent = eventStorage.find(failEvent.getEventId());
        Assert.assertNull("testEvent success publish", testStorableEvent);
    }
}
