package org.github.event.domain;

/**
 * @author chenjx
 */
public interface CustomerRepository {
    CustomerId next();

    Customer findById(CustomerId customerId);

    void save(Customer customer);
}
