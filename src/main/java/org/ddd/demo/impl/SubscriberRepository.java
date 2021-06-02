package org.ddd.demo.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * @Author Michael
 */
public interface SubscriberRepository extends JpaRepository<SubscriberDO, Long> {
    Set<SubscriberDO> findByEventId(String eventId);
}
