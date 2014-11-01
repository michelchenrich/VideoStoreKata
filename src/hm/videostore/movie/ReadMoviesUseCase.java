package hm.videostore.movie;

import hm.videostore.data.Context;

public class ReadMoviesUseCase {
    public ReadMoviesResponse execute() {
        ReadMoviesResponse response = new ReadMoviesResponse();
        response.movies = Context.movieRepository.findAll();
        return response;
    }
}
