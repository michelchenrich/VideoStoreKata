package hm.videostore;

import hm.videostore.repository.EntityData;
import hm.videostore.repository.Repository;

import java.util.HashMap;
import java.util.Map;

class InMemoryRepository<TEntity extends EntityData> implements Repository<TEntity> {
    private Map<String, TEntity> entities = new HashMap<String, TEntity>();
    private int incrementalId;

    public void save(TEntity entity) {
        entities.put(entity.id, entity);
    }

    public TEntity findById(String id) {
        return entities.get(id);
    }

    public String getNextId() {
        return String.valueOf(++incrementalId);
    }
}