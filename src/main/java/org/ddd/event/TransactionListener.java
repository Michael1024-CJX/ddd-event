package org.ddd.event;

/**
 * @author Michael
 */
public interface TransactionListener {
    void afterCommit(TransactionCallback callback);
}
