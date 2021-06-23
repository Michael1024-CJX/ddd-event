package org.github.event.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjx
 */
public abstract class InMemoryRepository<ID, E> {
    private final Map<ID, E> memoryDB = new ConcurrentHashMap<>();

    public E findById(ID id) {
        return memoryDB.get(id);
    }

    public void save(ID id, E e) {
        memoryDB.put(id, e);
    }

    public List<E> findAll(){
        return new ArrayList<>(memoryDB.values());
    }
}
