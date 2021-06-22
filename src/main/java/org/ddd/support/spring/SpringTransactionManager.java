package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.TransactionCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Michael
 */
@Slf4j
@Component
public class SpringTransactionManager {
    public void registerAfterCommitCallback(TransactionCallback callback){
        if (isOpenTransaction()){
            callbackAfterCommitTransaction(callback);
        }else {
            callbackNoTransaction(callback);
        }
    }

    private void callbackAfterCommitTransaction(TransactionCallback callback) {
        final TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                callback.callback();
            }
        };
        registerTransactionCallback(transactionSynchronization);
    }

    public void registerBeforeCommitCallback(TransactionCallback callback){
        if (isOpenTransaction()){
            callbackBeforeCommitTransaction(callback);
        }else {
            callbackNoTransaction(callback);
        }
    }

    private void callbackBeforeCommitTransaction(TransactionCallback callback) {
        final TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
            @Override
            public void beforeCommit(boolean readOnly) {
                callback.callback();
            }
        };
        registerTransactionCallback(transactionSynchronization);
    }

    public boolean isOpenTransaction() {
        return TransactionSynchronizationManager.isSynchronizationActive();
    }

    private void callbackNoTransaction(TransactionCallback callback) {
        log.warn("transaction is not open, callback method is not in transaction");
        callback.callback();
    }

    private void registerTransactionCallback(TransactionSynchronization transactionSynchronization){
        TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
    }
}
