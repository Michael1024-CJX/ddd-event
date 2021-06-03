package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
/**
 * @author Michael
 */
@Component
@Slf4j
public class CheckEventScheduled {

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
            DefaultExecutor.execute(retryTask::retry);
        }
    }
}
