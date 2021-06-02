package org.ddd.event.domain;

/**
 * @author Michael
 */
public interface TransactionListener {
    void afterCommit(TransactionCallback callback);
}
