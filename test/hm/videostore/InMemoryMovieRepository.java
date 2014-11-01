package hm.videostore;

import hm.videostore.data.Movie;

class InMemoryMovieRepository extends InMemoryRepository<Movie> {
    protected Movie makeCopy(Movie movie) {
        Movie copy = new Movie();
        copy.id = movie.id;
        copy.name = movie.name;
        copy.type = movie.type;
        return copy;
    }
}