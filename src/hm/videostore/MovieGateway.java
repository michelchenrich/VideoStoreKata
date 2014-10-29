package hm.videostore;

public interface MovieGateway {
    Movie create();

    void save(Movie movie);

    Movie findById(String movieId);
}
