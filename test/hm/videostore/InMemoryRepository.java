package hm.videostore;

import hm.videostore.data.Entity;
import hm.videostore.data.Repository;

import java.util.HashMap;
import java.util.Map;

abstract class InMemoryRepository<TEntity extends Entity> implements Repository<TEntity> {
    private Map<String, TEntity> entities = new HashMap<String, TEntity>();
    private int incrementalId;

    public void save(TEntity entity) {
        entities.put(entity.id, makeCopy(entity));
    }

    public TEntity findById(String id) {
        return entities.get(id);
    }

    public String getNextId() {
        return String.valueOf(++incrementalId);
    }

    protected abstract TEntity makeCopy(TEntity movie);
}
