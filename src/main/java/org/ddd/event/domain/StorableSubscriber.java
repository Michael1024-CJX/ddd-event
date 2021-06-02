package org.ddd.event.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private long consumedOn;

    public StorableSubscriber(String eventId, String subscriberType) {
        this.eventId = eventId;
        this.subscriberType = subscriberType;
    }

    public void consume() {
        if (!consumed){
            this.consumed = true;
            consumedOn = System.currentTimeMillis();
        }
    }

    // JPA Entity Id
    private Long id;
}
