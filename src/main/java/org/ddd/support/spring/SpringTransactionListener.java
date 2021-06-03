package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.domain.TransactionCallback;
import org.ddd.event.domain.TransactionListener;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 */
@Slf4j
@Component
public class SpringTransactionListener implements TransactionListener {

    @Override
    public void afterCommit(TransactionCallback callback) {
        SpringTransactionManager.registerAfterCommitCallback(callback);
    }

    private void callbackWithNoTransaction(TransactionCallback callback) {
        log.warn("当前保存事件的操作不在事务中进行");
        callback.callback();
    }
}
