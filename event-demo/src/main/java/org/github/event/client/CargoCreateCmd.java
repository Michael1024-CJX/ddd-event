package org.github.event.client;

/**
 * @author chenjx
 */
public class CargoCreateCmd {
    private String customerId;
    private String origin;
    private String target;

    public CargoCreateCmd(String customerId, String origin, String target) {
        this.customerId = customerId;
        this.origin = origin;
        this.target = target;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getTarget() {
        return target;
    }
}
