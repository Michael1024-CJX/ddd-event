package org.ddd.support.spring;

import org.ddd.event.TransactionCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Michael
 */
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
        callback.callback();
    }

    private void registerTransactionCallback(TransactionSynchronization transactionSynchronization){
        TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
    }
}
