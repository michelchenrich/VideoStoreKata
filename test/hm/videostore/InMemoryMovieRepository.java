package hm.videostore;

import hm.videostore.repository.MovieData;

class InMemoryMovieRepository extends InMemoryRepository<MovieData> {
    protected MovieData makeCopy(MovieData movie) {
        MovieData copy = new MovieData();
        copy.id = movie.id;
        copy.name = movie.name;
        copy.typeCode = movie.typeCode;
        return copy;
    }
}