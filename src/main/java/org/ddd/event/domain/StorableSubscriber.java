package org.ddd.event.domain;

/**
 * @author Michael
 * @date 2021/5/31 14:03
 */
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
        this.consumed = true;
        consumedOn = System.currentTimeMillis();
    }

    public String getEventId() {
        return eventId;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public long getConsumedOn() {
        return consumedOn;
    }


    @Override
    public String toString() {
        return "StorableSubscriber{" +
                "eventId='" + eventId + '\'' +
                ", subscriberType='" + subscriberType + '\'' +
                ", consumed=" + consumed +
                ", consumedOn=" + consumedOn +
                '}';
    }
}
