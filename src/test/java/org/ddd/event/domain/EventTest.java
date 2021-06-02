package org.ddd.event.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael
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
