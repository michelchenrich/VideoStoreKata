package hm.videostore;

import hm.videostore.data.Context;
import hm.videostore.data.Movie;
import hm.videostore.movie.CreateMovieRequest;
import hm.videostore.movie.CreateMovieUseCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

public class CreateMovieTest {
    private InMemoryRepository<Movie> inMemoryMovieRepository;

    @Before
    public void setUp() {
        inMemoryMovieRepository = new InMemoryRepository<Movie>();
        Context.movieRepository = inMemoryMovieRepository;
    }

    @Test
    public void afterCreatingMovie_GatewayShouldTheEntry() {
        String id1 = createMovie(Movie.Type.REGULAR, "Movie Name 1");
        String id2 = createMovie(Movie.Type.CHILDRENS, "Movie Name 2");

        Movie movie = inMemoryMovieRepository.findById(id1);
        assertEquals(id1, movie.id);
        assertEquals("Movie Name 1", movie.name);
        assertEquals(Movie.Type.REGULAR, movie.type);

        movie = inMemoryMovieRepository.findById(id2);
        assertEquals(id2, movie.id);
        assertEquals("Movie Name 2", movie.name);
        assertEquals(Movie.Type.CHILDRENS, movie.type);
    }

    @Test
    public void afterSavingAMovie_RepositoryShouldNotReflectTransientChanges() {
        Movie movie = new Movie();
        movie.id = "1";
        movie.name = "a";

        inMemoryMovieRepository.save(movie);

        movie.name = "b";

        assertNotEquals(movie.name, inMemoryMovieRepository.findById(movie.id).name);
    }

    private String createMovie(Movie.Type type, String name) {
        CreateMovieRequest request = new CreateMovieRequest();
        request.type = type;
        request.name = name;
        return new CreateMovieUseCase().execute(request).id;
    }
}
