package org.ddd.demo.impl;

import com.alibaba.fastjson.JSONObject;
import org.ddd.event.domain.Event;
import org.ddd.event.domain.StorableEvent;
import org.ddd.event.domain.StorableSubscriber;
import org.ddd.event.domain.SubscriberId;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Michael
 */
public class EventConverter {
    public EventDO toEventDO(StorableEvent storableEvent) {
        EventDO eventDO = new EventDO();
        Event event = storableEvent.getEvent();
        eventDO.setEventId(event.getEventId());
        eventDO.setEventType(event.getClass().getTypeName());
        eventDO.setOccurredOn(Timestamp.from(event.occurredOn()));
        eventDO.setSourceType(event.getSource().getClass().getTypeName());
        eventDO.setNumOfConsumer(storableEvent.getNumOfConsumer());
        eventDO.setStatus(storableEvent.getStatus());

        String jsonEvent = toJsonEvent(event);
        eventDO.setJsonEvent(jsonEvent);
        return eventDO;
    }

    private String toJsonEvent(Event event) {
        return JSONObject.toJSONString(event);
    }

    public Set<SubscriberDO> toSubscriberDO(Set<StorableSubscriber> subscribers) {
        return subscribers
                .stream()
                .map(this::toSubscriberDO)
                .collect(Collectors.toSet());
    }

    public SubscriberDO toSubscriberDO(StorableSubscriber subscriber) {
        SubscriberDO subscriberDO = new SubscriberDO();
        subscriberDO.setConsumed(subscriber.isConsumed());
        Instant consumedOn = subscriber.getConsumedOn();
        if (consumedOn != null) {
            subscriberDO.setConsumedOn(Timestamp.from(consumedOn));
        }
        final SubscriberId subscriberId = subscriber.getSubscriberId();
        subscriberDO.setEventId(subscriberId.eventId());
        subscriberDO.setSubscriberType(subscriberId.subscriberType());
        return subscriberDO;
    }

    public StorableEvent toEntity(EventDO eventDO, Set<SubscriberDO> subscriberDOS) {
        try {
            Class<?> aClass = Class.forName(eventDO.getEventType());
            Event object = (Event) JSONObject.parseObject(eventDO.getJsonEvent(), aClass);
            StorableEvent event = StorableEvent.newStorableEvent(object);
            event.setId(eventDO.getId());
            event.setNumOfConsumer(eventDO.getNumOfConsumer());
            event.setStatus(eventDO.getStatus());
            event.setSubscribers(toEntityMap(subscriberDOS));
            return event;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("eventType class not found [" + eventDO.getEventType() + "]", e);
        }
    }

    private Map<SubscriberId, StorableSubscriber> toEntityMap(Set<SubscriberDO> subscriberDOS) {
        return subscriberDOS
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toMap(StorableSubscriber::getSubscriberId, Function.identity()));
    }

    private StorableSubscriber toEntity(SubscriberDO subscriberDO) {
        StorableSubscriber storableSubscriber = new StorableSubscriber(
                subscriberDO.getEventId(),
                subscriberDO.getSubscriberType());

        storableSubscriber.setConsumed(subscriberDO.isConsumed());
        Timestamp consumedOn = subscriberDO.getConsumedOn();
        if (consumedOn != null){
            storableSubscriber.setConsumedOn(consumedOn.toInstant());
        }
        storableSubscriber.setId(subscriberDO.getId());

        return storableSubscriber;
    }
}
