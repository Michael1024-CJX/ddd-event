package org.ddd.demo.impl;

import lombok.Data;
import org.ddd.event.domain.StorableEvent;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Michael
 */
@Data
@Entity
public class EventDO {
    @Id
    @GeneratedValue
    private long id;
    private String eventId;
    private String eventType;
    private Timestamp occurredOn;
    private String sourceType;
    @Enumerated(EnumType.STRING)
    private StorableEvent.EventStatus status;
    private int numOfConsumer;
    private String jsonEvent;
}
