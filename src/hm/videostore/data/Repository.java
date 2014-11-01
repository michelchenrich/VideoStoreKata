package hm.videostore.data;

public interface Repository<TEntity extends Entity> {
    void save(TEntity entity);

    TEntity findById(String id);

    String getNextId();
}
