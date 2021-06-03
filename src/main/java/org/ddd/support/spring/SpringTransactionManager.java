package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.domain.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Michael
 */
@Slf4j
public class SpringTransactionManager {
    public static boolean isOpenTransaction() {
        return TransactionSynchronizationManager.isSynchronizationActive();
    }

    public static void registerAfterCommitCallback(TransactionCallback callback){
        if (SpringTransactionManager.isOpenTransaction()){
            callbackAfterCommitTransaction(callback);
        }else {
            callbackNoTransaction(callback);
        }
    }

    private static void callbackAfterCommitTransaction(TransactionCallback callback) {
        final TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                callback.callback();
            }
        };
        registerTransactionCallback(transactionSynchronization);
    }

    public static void registerBeforeCommitCallback(TransactionCallback callback){
        if (SpringTransactionManager.isOpenTransaction()){
            callbackBeforeCommitTransaction(callback);
        }else {
            callbackNoTransaction(callback);
        }
    }

    private static void callbackBeforeCommitTransaction(TransactionCallback callback) {
        final TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
            @Override
            public void beforeCommit(boolean readOnly) {
                callback.callback();
            }
        };
        registerTransactionCallback(transactionSynchronization);
    }

    private static void callbackNoTransaction(TransactionCallback callback) {
        log.warn("transaction is not open, callback method is not in transaction");
        callback.callback();
    }

    private static void registerTransactionCallback(TransactionSynchronization transactionSynchronization){
        TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
    }
}
