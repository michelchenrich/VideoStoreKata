package hm.videostore;

import hm.videostore.data.Entity;
import hm.videostore.data.Repository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class InMemoryRepository<TEntity extends Entity> implements Repository<TEntity> {
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

    private TEntity makeCopy(TEntity entity) {
        try {
            Class entityClass = entity.getClass();
            TEntity copy = (TEntity) entityClass.getConstructor().newInstance();
            for (Field field : entityClass.getFields()) field.set(copy, field.get(entity));
            return copy;
        } catch (Exception original) {
            throw new RuntimeException(original);
        }
    }
}