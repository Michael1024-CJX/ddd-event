package org.github.event.domain;

import java.util.Objects;

/**
 * @author chenjx
 */
public class Route {
    private String originLocal;
    private String targetLocal;

    public Route(String originLocal, String targetLocal) {
        this.originLocal = originLocal;
        this.targetLocal = targetLocal;
    }

    public String getOriginLocal() {
        return originLocal;
    }

    public String getTargetLocal() {
        return targetLocal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(originLocal, route.originLocal) &&
                Objects.equals(targetLocal, route.targetLocal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originLocal, targetLocal);
    }
}
