package org.ddd.demo.impl;

import org.ddd.event.domain.EventStorage;
import org.ddd.event.domain.StorableEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author Michael
 */
@Component
public class JpaEventStorage implements EventStorage {
    private EventRepository eventRepository;
    private SubscriberRepository subscriberRepository;

    public JpaEventStorage(EventRepository eventRepository, SubscriberRepository subscriberRepository) {
        this.eventRepository = eventRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    @Transactional
    public void storeEvent(StorableEvent event) {
        EventConverter eventConverter = new EventConverter();
        EventDO eventDO = eventConverter.toEventDO(event);
        Set<SubscriberDO> subscriberDOS = eventConverter.toSubscriberDO(event.getSubscribers());
        eventRepository.save(eventDO);
        subscriberRepository.saveAll(subscriberDOS);
    }

    @Override
    public StorableEvent find(String eventId) {
        EventDO eventDO = eventRepository.findByEventId(eventId);
        if (eventDO == null){
            return null;
        }
        Set<SubscriberDO> subscriberDOS = subscriberRepository.findByEventId(eventId);
        EventConverter eventConverter = new EventConverter();
        return eventConverter.toEntity(eventDO, subscriberDOS);
    }

    @Override
    public List<StorableEvent> findNotFinishEvent() {
        return null;
    }
}
