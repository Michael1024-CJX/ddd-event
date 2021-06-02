package org.ddd.demo.impl;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Michael
 */
public interface EventRepository extends JpaRepository<EventDO, Long> {
    EventDO findByEventId(String eventId);
}
