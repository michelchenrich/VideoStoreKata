package hm.videostore;

import java.util.HashMap;
import java.util.Map;

class InMemoryMovieGateway implements MovieGateway {
    public Map<String, Movie> movies = new HashMap<String, Movie>();
    private int incrementalId = 0;

    public Movie create() {
        return new Movie(String.valueOf(++incrementalId));
    }

    public void save(Movie movie) {
        movies.put(movie.getId(), movie);
    }

    public Movie findById(String movieId) {
        return movies.get(movieId);
    }
}