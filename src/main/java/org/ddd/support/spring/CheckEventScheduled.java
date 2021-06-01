package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjx@dist.com.cn
 * @date 2021/6/1 13:22
 */
@Component
@Slf4j
public class CheckEventScheduled {
//    private final String taskCron = "0 0/1 * ?"; //每分钟执行一次
    private static Executor executor = new ThreadPoolExecutor(
            10,
            10,
            6,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50));

    private EventStore eventStore;
    private SubscriberHolder subscriberHolder;

    public CheckEventScheduled(EventStore eventStore, SubscriberHolder subscriberHolder) {
        this.eventStore = eventStore;
        this.subscriberHolder = subscriberHolder;
    }

    @Scheduled(fixedRate = 1000 * 30)
    public void checkEventFinished() {
        log.info("开始执行任务[{}]", "checkEventFinished");
        List<StorableEvent> notFinishEvent = eventStore.findNotFinishEvent();
        if (CollectionUtils.isEmpty(notFinishEvent)){
            return;
        }
        for (StorableEvent storableEvent : notFinishEvent) {
            executor.execute(() -> reConsumerEvent(storableEvent));
        }
    }

    public void reConsumerEvent(StorableEvent storableEvent) {
        Set<StorableSubscriber> notHandleSubscriber = storableEvent.getNotHandleSubscriber();

        for (StorableSubscriber storableSubscriber : notHandleSubscriber) {
            executor.execute(() -> reHandleEvent(storableEvent, storableSubscriber.getSubscriberType()));
        }
    }

    @Transactional
    public void reHandleEvent(StorableEvent storableEvent, String subscriberType){
        System.out.println("事务是否存在：" + TransactionSynchronizationManager.isSynchronizationActive());
        executeSubscriber(subscriberType, storableEvent.getEvent());
        log.info("更新storableEvent[{}]", storableEvent);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void executeSubscriber(String subscriberType, Event event) {
        SubscriberWrapper subscriber = subscriberHolder.getSubscriber(subscriberType);
        EventSubscriber eventSubscriber = subscriber.getEventSubscriber();
        eventSubscriber.handle(event);
    }
}
