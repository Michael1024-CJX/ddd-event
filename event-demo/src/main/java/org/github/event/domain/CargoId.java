package org.github.event.domain;

import java.util.Objects;

/**
 * @author chenjx
 */
public class CargoId {
    private String cargoId;

    public CargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoId() {
        return cargoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoId cargoId1 = (CargoId) o;
        return Objects.equals(cargoId, cargoId1.cargoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cargoId);
    }
}
