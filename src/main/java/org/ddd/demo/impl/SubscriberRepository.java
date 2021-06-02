package org.ddd.demo.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * @Author chenjx@dist.com.cn
 * @Date 2021/6/2 9:36
 */
public interface SubscriberRepository extends JpaRepository<SubscriberDO, Long> {
    Set<SubscriberDO> findByEventId(String eventId);
}
