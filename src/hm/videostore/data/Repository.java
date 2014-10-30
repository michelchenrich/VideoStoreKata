package hm.videostore.data;

public interface Repository<TEntity extends EntityData> {
    void save(TEntity entity);

    TEntity findById(String id);

    String getNextId();
}
