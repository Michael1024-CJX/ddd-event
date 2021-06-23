package org.github.event.domain;

/**
 * @author chenjx
 */
public interface TransporterRepository {
    Transporter findById(String id);

    Transporter findByRoute(Route route);

    void save(Transporter transporter);
}
