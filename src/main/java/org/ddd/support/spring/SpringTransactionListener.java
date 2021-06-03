package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.ddd.event.domain.TransactionCallback;
import org.ddd.event.domain.TransactionListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Michael
 */
@Slf4j
@Component
public class SpringTransactionListener implements TransactionListener {

    @Override
    public void afterCommit(TransactionCallback callback) {
        if (SpringTransactionManager.isOpenTransaction()){
            SpringTransactionManager.registerAfterCommitCallback(callback);
        }else {
            callbackWithNoTransaction(callback);
        }
    }

    private void callbackWithNoTransaction(TransactionCallback callback) {
        log.warn("当前保存事件的操作不在事务中进行");
        callback.callback();
    }
}
