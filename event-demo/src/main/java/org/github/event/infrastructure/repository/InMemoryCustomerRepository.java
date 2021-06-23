package org.github.event.infrastructure.repository;

import org.github.event.domain.Customer;
import org.github.event.domain.CustomerId;
import org.github.event.domain.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author chenjx
 */
@Component
public class InMemoryCustomerRepository extends InMemoryRepository<CustomerId, Customer> implements CustomerRepository {
    {
        final Customer michael = new Customer(next(), "Michael");
        System.out.println("Michael:" + michael.id().getCustomerId());
        save(michael);
    }

    @Override
    public CustomerId next() {
        return new CustomerId(UUID.randomUUID().toString());
    }

    @Override
    public void save(Customer customer) {
        super.save(customer.id(), customer);
    }
}
