package org.ddd.demo.impl;

import lombok.ToString;
import org.ddd.event.domain.EventObject;

/**
 * @author Michael
 */
@ToString
public class FailEvent extends EventObject {
    public FailEvent(Object source) {
        super(source);
    }
}
