package org.ddd.support.spring;

import org.ddd.event.TransactionCallback;
import org.ddd.event.TransactionListener;

/**
 * @author Michael
 */
public class SpringTransactionListener implements TransactionListener {
    private SpringTransactionManager springTransactionManager;

    public SpringTransactionListener(SpringTransactionManager springTransactionManager) {
        this.springTransactionManager = springTransactionManager;
    }

    @Override
    public void afterCommit(TransactionCallback callback) {
        springTransactionManager.registerAfterCommitCallback(callback);
    }
}
