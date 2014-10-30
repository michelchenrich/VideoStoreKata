package hm.videostore;

import static hm.videostore.Context.movieRepository;
import hm.videostore.repository.MovieData;

public class CreateMovieUseCase {
    private int typeCode;
    private String name;

    public CreateMovieUseCase(int typeCode, String name) {
        this.typeCode = typeCode;
        this.name = name;
    }

    public String execute() {
        MovieData movie = new MovieData();
        movie.id = movieRepository.getNextId();
        movie.name = name;
        movie.typeCode = typeCode;
        movieRepository.save(movie);
        return movie.id;
    }
}
