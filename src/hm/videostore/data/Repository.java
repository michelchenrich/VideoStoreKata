package hm.videostore.data;

import java.util.Collection;

public interface Repository<TEntity extends Entity> {
    void save(TEntity entity);

    TEntity findById(String id);

    String getNextId();

    Collection<TEntity> findAll();
}
