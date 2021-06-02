package org.ddd.event.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * @author Michael
 * @date 2021/5/31 14:03
 */
@Setter
@Getter
@ToString
public class StorableSubscriber {
    private String eventId;
    private String subscriberType;
    private boolean consumed;
    private Instant consumedOn;

    public StorableSubscriber(String eventId, String subscriberType) {
        this.eventId = eventId;
        this.subscriberType = subscriberType;
    }

    public void consume() {
        if (!consumed){
            this.consumed = true;
            consumedOn = Instant.now();
        }
    }

    // JPA Entity Id
    private Long id;
}
