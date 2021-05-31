package org.ddd.support.spring;

import org.aspectj.lang.annotation.Pointcut;
import org.ddd.event.domain.TransactionCallback;
import org.ddd.event.domain.TransactionListener;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Michael
 * @date 2021/5/29 10:25
 */
public class SpringTransactionListener implements TransactionListener {

    @Override
    public void afterCommit(TransactionCallback callback) {
        if (TransactionSynchronizationManager.isSynchronizationActive()){
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    callback.callback();
                }
            });
        }else {
            callback.callback();
        }
    }
}
