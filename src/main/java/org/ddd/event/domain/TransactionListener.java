package org.ddd.event.domain;

/**
 * @author Michael
 * @date 2021/5/26 10:55
 */
public interface TransactionListener {
    void afterCommit(TransactionCallback callback);
}
