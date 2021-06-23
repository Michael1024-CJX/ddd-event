package org.github.event.infrastructure.repository;

import org.github.event.domain.Route;
import org.github.event.domain.Transporter;
import org.github.event.domain.TransporterRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author chenjx
 */
@Component
public class InMemoryTransporterRepository extends InMemoryRepository<String, Transporter> implements TransporterRepository {

    {
        final Route route = new Route("A", "B");
        final Transporter transporter = new Transporter("1",route);
        save(transporter);
    }

    @Override
    public Transporter findByRoute(Route route) {
        final List<Transporter> all = findAll();

        final Optional<Transporter> first = all.stream()
                .filter(transporter -> transporter.getRoute().equals(route))
                .findFirst();

        return first.orElse(null);
    }

    @Override
    public void save(Transporter transporter) {
        super.save(transporter.getTransporterId(), transporter);
    }
}
