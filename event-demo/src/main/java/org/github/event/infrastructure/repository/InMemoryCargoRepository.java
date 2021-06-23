package org.github.event.infrastructure.repository;

import org.github.event.domain.Cargo;
import org.github.event.domain.CargoId;
import org.github.event.domain.CargoRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author chenjx
 */
@Component
public class InMemoryCargoRepository extends InMemoryRepository<CargoId, Cargo> implements CargoRepository {

    @Override
    public CargoId nextId() {
        return new CargoId(UUID.randomUUID().toString());
    }

    @Override
    public void save(Cargo cargo) {
        super.save(cargo.getCargoId(), cargo);
    }
}
