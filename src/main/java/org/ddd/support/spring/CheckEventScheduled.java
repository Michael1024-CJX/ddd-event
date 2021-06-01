package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael
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
            RetryOnSubscribeFail retryTask = new RetryOnSubscribeFail(subscriberHolder, storableEvent);
            executor.execute(retryTask::reConsumerEvent);
        }
    }
}
