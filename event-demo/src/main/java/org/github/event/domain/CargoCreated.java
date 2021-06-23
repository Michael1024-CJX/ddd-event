package org.github.event.client;

import org.ddd.event.EventObject;
import org.github.event.domain.CargoId;
import org.github.event.domain.CustomerId;
import org.github.event.domain.Route;

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
