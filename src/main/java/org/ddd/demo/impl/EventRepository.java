package org.ddd.demo.impl;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenjx@dist.com.cn
 * @date 2021/6/2 9:18
 */
public interface EventRepository extends JpaRepository<EventDO, Long> {
    EventDO findByEventId(String eventId);
}
