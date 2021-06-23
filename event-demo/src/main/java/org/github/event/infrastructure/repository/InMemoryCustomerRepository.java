package org.github.event.infrastructure.repository;

import org.github.event.domain.Customer;
import org.github.event.domain.CustomerId;

/**
 * @author chenjx
 */
public class InMemoryCustomerRepository extends InMemoryRepository<CustomerId, Customer> implements org.github.event.domain.CustomerRepository {
    @Override
    public void save(Customer customer) {

    }
}
