package org.github.event.application.subsrciber;

import lombok.extern.slf4j.Slf4j;
import org.ddd.event.EventSubscriber;
import org.github.event.domain.*;
import org.springframework.stereotype.Component;

/**
 * @author chenjx
 */
@Component
@Slf4j
public class CargoHandlerSubscriber implements EventSubscriber<CargoHandled> {
    private final CustomerRepository customerRepository;
    private final CargoRepository cargoRepository;

    public CargoHandlerSubscriber(CargoRepository cargoRepository, CustomerRepository customerRepository) {
        this.cargoRepository = cargoRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void handle(CargoHandled event) {
        log.info("货物处理事件：" + event.eventType() + ":" + event.getEventId());
        final Cargo cargo = cargoRepository.findById(event.getCargoId());
        final Customer customer = customerRepository.findById(cargo.getCustomerId());

        customer.notice("当前货物状态：" + event.getHandleType());

        customerRepository.save(customer);
    }
}
