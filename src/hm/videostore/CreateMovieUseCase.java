package hm.videostore;

import static hm.videostore.Context.movieRepository;
import hm.videostore.data.MovieData;
import hm.videostore.data.MovieType;

public class CreateMovieUseCase {
    private MovieType type;
    private String name;

    public CreateMovieUseCase(MovieType type, String name) {
        this.type = type;
        this.name = name;
    }

    public String execute() {
        MovieData movie = new MovieData();
        movie.id = movieRepository.getNextId();
        movie.name = name;
        movie.type = type;
        movieRepository.save(movie);
        return movie.id;
    }
}
