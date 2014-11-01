package hm.videostore;

import hm.videostore.data.Context;
import hm.videostore.data.Movie;
import static hm.videostore.data.Movie.Type;
import static hm.videostore.data.Movie.Type.CHILDRENS;
import static hm.videostore.data.Movie.Type.REGULAR;
import hm.videostore.movie.CreateMovieRequest;
import hm.videostore.movie.CreateMovieUseCase;
import hm.videostore.movie.ReadMoviesUseCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class CreateMovieTest {
    private Collection<Movie> movies;

    @Before
    public void setUp() {
        Context.movieRepository = new InMemoryRepository<Movie>();
    }

    @Test
    public void afterCreatingMovie_GatewayShouldTheEntry() {
        String id1 = createMovie(REGULAR, "Movie Name 1");
        String id2 = createMovie(CHILDRENS, "Movie Name 2");
        readMovies();
        assertMovieExists(id1, "Movie Name 1", REGULAR);
        assertMovieExists(id2, "Movie Name 2", CHILDRENS);
    }

    private void readMovies() {
        movies = new ReadMoviesUseCase().execute().movies;
    }

    private void assertMovieExists(String id, String name, Type type) {
        for (Movie movie : movies) {
            if (movie.id.equals(id)) {
                assertEquals(id, movie.id);
                assertEquals(name, movie.name);
                assertEquals(type, movie.type);
                return;
            }
        }
        fail();
    }

    private String createMovie(Type type, String name) {
        CreateMovieRequest request = new CreateMovieRequest();
        request.type = type;
        request.name = name;
        return new CreateMovieUseCase().execute(request).id;
    }
}
