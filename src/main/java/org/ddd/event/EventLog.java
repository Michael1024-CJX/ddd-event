package org.ddd.event;

import lombok.Data;

import java.util.Date;

/**
 * @author chenjx
 */
@Data
public class EventLog {
    private String eventId;

    private String eventType;

    private Date consumerTime;

    private String subscriberType;

    public EventLog() {
        consumerTime = new Date();
    }
}
