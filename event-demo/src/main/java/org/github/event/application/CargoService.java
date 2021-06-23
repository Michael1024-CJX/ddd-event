package org.github.event.application;

import org.ddd.event.EventPublisher;
import org.github.event.client.CargoCreateCmd;
import org.github.event.domain.CargoCreated;
import org.github.event.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenjx
 */
@Component
public class CargoService {
    private CargoRepository cargoRepository;
    private CustomerRepository customerRepository;
    private EventPublisher eventPublisher;

    public CargoService(CargoRepository cargoRepository,
                        CustomerRepository customerRepository,
                        EventPublisher eventPublisher) {
        this.cargoRepository = cargoRepository;
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void creatCargo(CargoCreateCmd cargoCreateCmd){
        final Customer customer = customerRepository.findById(new CustomerId(cargoCreateCmd.getCustomerId()));
        final Cargo cargo = new Cargo(cargoRepository.nextId(), customer.id());
        final Route route = new Route(cargoCreateCmd.getOrigin(), cargoCreateCmd.getTarget());
        cargo.setRoute(route);

        cargoRepository.save(cargo);

        final CargoCreated cargoCreated = new CargoCreated(this, cargo.getCargoId(), customer.id(), route);
        eventPublisher.publishEvent(cargoCreated);
    }
}
