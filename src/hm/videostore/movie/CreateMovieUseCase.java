package hm.videostore.movie;

import static hm.videostore.data.Context.movieRepository;
import hm.videostore.data.Movie;

public class CreateMovieUseCase {
    public CreateMovieResponse execute(CreateMovieRequest request) {
        Movie movie = new Movie();
        movie.id = movieRepository.getNextId();
        movie.name = request.name;
        movie.type = request.type;
        movieRepository.save(movie);
        CreateMovieResponse response = new CreateMovieResponse();
        response.id = movie.id;
        return response;
    }
}
