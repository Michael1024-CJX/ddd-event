package org.ddd.demo.impl;

import lombok.ToString;
import org.ddd.event.domain.EventObject;

/**
 * @author Michael
 */
@ToString
public class TestEvent extends EventObject {
    public TestEvent(Object source) {
        super(source);
    }
}
