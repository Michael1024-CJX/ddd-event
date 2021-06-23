package org.github.event.domain;

/**
 * @author chenjx
 */
public class Cargo {
    private CargoId cargoId;
    private Route route;
    private CustomerId customerId;

    public Cargo(CargoId cargoId, CustomerId customerId) {
        this.cargoId = cargoId;
        this.customerId = customerId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public CargoId getCargoId() {
        return cargoId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }
}
