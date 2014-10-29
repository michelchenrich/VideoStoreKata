package hm.videostore;

import hm.videostore.repository.EntityData;
import hm.videostore.repository.Repository;

import java.util.HashMap;
import java.util.Map;

class InMemoryRepository<TEntity extends EntityData> implements Repository<TEntity> {
    private Map<String, TEntity> entities = new HashMap<String, TEntity>();
    private int incrementalId;

    public void save(TEntity entity) {
        try {
            entities.put(entity.id, (TEntity) entity.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public TEntity findById(String id) {
        return entities.get(id);
    }

    public String getNextId() {
        return String.valueOf(++incrementalId);
    }
}