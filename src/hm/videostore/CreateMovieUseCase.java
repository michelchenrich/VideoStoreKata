package hm.videostore;

import hm.videostore.repository.MovieData;

public class CreateMovieUseCase {
    private final int typeCode;
    private final String name;

    public CreateMovieUseCase(int typeCode, String name) {
        this.typeCode = typeCode;
        this.name = name;
    }

    public String execute() {
        MovieData movie = new MovieData();
        movie.id = Context.movieRepository.getNextId();
        movie.name = name;
        movie.typeCode = typeCode;
        Context.movieRepository.save(movie);
        return movie.id;
    }
}
