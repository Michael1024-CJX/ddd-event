package org.ddd.support.spring;

import org.ddd.event.TransactionCallback;
import org.ddd.event.TransactionListener;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 */
@Component
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
