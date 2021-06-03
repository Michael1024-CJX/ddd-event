package org.ddd.event.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * @author Michael
 */
@Setter
@Getter
@ToString
public class StorableSubscriber {
    private SubscriberId subscriberId;
    private boolean consumed;
    private Instant consumedOn;

    public StorableSubscriber(String eventId, String subscriberType) {
        this.subscriberId = new SubscriberId(eventId, subscriberType);
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
