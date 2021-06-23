package org.github.event.domain;

import org.ddd.event.EventObject;

/**
 * @author chenjx
 */
public class CargoHandled extends EventObject {
    private HandleType handleType;
    private CargoId cargoId;

    public CargoHandled(Object source) {
        super(source);
    }

    public CargoHandled(Object source, HandleType handleType, CargoId cargoId) {
        super(source);
        this.handleType = handleType;
        this.cargoId = cargoId;
    }

    public HandleType getHandleType() {
        return handleType;
    }

    public CargoId getCargoId() {
        return cargoId;
    }
}
