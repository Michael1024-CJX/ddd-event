package org.github.event.domain;

/**
 * @author chenjx
 */
public interface CargoRepository {
    CargoId nextId();

    Cargo findById(CargoId cargoId);

    void save(Cargo cargo);
}
