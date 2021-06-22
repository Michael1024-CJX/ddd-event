package org.ddd.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.TransactionCallback;
import org.ddd.event.TransactionListener;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 */
@Slf4j
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
