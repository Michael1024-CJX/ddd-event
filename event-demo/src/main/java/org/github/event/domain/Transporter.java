package org.github.event.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chenjx
 */
public class Transporter {
    private String transporterId;
    private Route route;
    private Set<Cargo> cargoes;

    public Transporter(String transporterId, Route route) {
        this.transporterId = transporterId;
        this.route = route;
        this.cargoes = new HashSet<>();
    }

    public void loadCargo(Cargo cargo){
        if (isOverLoad()){
            throw new RuntimeException("当前货物已满");
        }
        cargoes.add(cargo);
    }

    private boolean isOverLoad(){
        return currentQuantity() > 3;
    }

    private int currentQuantity(){
        return cargoes.size();
    }

    public String getTransporterId() {
        return transporterId;
    }

    public Route getRoute() {
        return route;
    }

    public Set<Cargo> getCargoes() {
        return cargoes;
    }
}
