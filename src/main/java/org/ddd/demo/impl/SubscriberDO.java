package org.ddd.demo.impl;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Michael
 */
@Data
@Entity
public class SubscriberDO {
    @Id
    @GeneratedValue
    private long id;
    private String eventId;
    private String subscriberType;
    private boolean consumed;
    private Timestamp consumedOn;
}
