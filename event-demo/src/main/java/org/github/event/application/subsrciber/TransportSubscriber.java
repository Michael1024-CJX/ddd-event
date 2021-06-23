package org.github.event.application.subsrciber;

import org.ddd.event.EventPublisher;
import org.ddd.event.EventSubscriber;
import org.github.event.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenjx
 */
@Component
public class TransportSubscriber implements EventSubscriber<CargoCreated> {
    private final TransporterRepository transporterRepository;
    private final CargoRepository cargoRepository;
    private final EventPublisher eventPublisher;

    public TransportSubscriber(TransporterRepository transporterRepository, CargoRepository cargoRepository, EventPublisher eventPublisher) {
        this.transporterRepository = transporterRepository;
        this.cargoRepository = cargoRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(CargoCreated event) {
        final Transporter transporter = transporterRepository.findByRoute(event.getRoute());
        transporter.loadCargo(cargoRepository.findById(event.getCargoId()));
        final CargoHandled cargoHandled = new CargoHandled(this, HandleType.LOAD, event.getCargoId());

        transporterRepository.save(transporter);
        eventPublisher.publishEvent(cargoHandled);
    }
}
