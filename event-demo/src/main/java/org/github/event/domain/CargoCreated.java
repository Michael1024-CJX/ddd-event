package org.github.event.domain;

import org.ddd.event.EventObject;

/**
 * @author chenjx
 */
public class CargoCreated extends EventObject {
    private CargoId cargoId;
    private CustomerId customerId;
    private Route route;

    public CargoCreated(Object source, CargoId cargoId, CustomerId customerId, Route route) {
        super(source);
        this.cargoId = cargoId;
        this.customerId = customerId;
        this.route = route;
    }

    public CargoCreated(Object source) {
        super(source);
    }

    public CargoId getCargoId() {
        return cargoId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Route getRoute() {
        return route;
    }
}
