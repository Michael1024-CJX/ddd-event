package org.ddd.event.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chenjx@dist.com.cn
 * @date 2021/6/1 21:38
 */
public class EventTest {
    @Test
    public void newEventTest() {
        EventObject eventObject = new EventObject(this) {{
        }};

        Assert.assertEquals("source is not this", this, eventObject.source);
        Assert.assertNotNull("event id is not inited", eventObject.eventId);
    }
}
