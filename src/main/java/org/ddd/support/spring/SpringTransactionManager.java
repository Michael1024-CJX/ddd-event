package org.ddd.support.spring;

import org.ddd.event.domain.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Michael
 */
public class SpringTransactionManager {
    public static boolean isOpenTransaction() {
        return TransactionSynchronizationManager.isSynchronizationActive();
    }

    public static void registerAfterCommitCallback(TransactionCallback callback){
        final TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                callback.callback();
            }
        };
        registerTransactionCallback(transactionSynchronization);
    }

    public static void registerBeforeCommitCallback(TransactionCallback callback){
        final TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
            @Override
            public void beforeCommit(boolean readOnly) {
                callback.callback();
            }
        };
        registerTransactionCallback(transactionSynchronization);
    }

    private static void registerTransactionCallback(TransactionSynchronization transactionSynchronization){
        TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
    }
}
