package hm.videostore;

import hm.videostore.repository.MovieData;
import hm.videostore.repository.Repository;

import java.util.HashMap;
import java.util.Map;

class InMemoryMovieRepository implements Repository<MovieData> {
    private Map<String, MovieData> entities = new HashMap<String, MovieData>();
    private int incrementalId;

    public void save(MovieData movie) {
        MovieData copy = new MovieData();
        copy.id = movie.id;
        copy.name = movie.name;
        copy.typeCode = movie.typeCode;
        entities.put(movie.id, copy);
    }

    public MovieData findById(String id) {
        return entities.get(id);
    }

    public String getNextId() {
        return String.valueOf(++incrementalId);
    }
}