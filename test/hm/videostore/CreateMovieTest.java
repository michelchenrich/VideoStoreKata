package hm.videostore;

import hm.videostore.data.MovieData;
import hm.videostore.data.MovieType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

public class CreateMovieTest {
    private InMemoryMovieRepository inMemoryMovieRepository;

    @Before
    public void setUp() {
        inMemoryMovieRepository = new InMemoryMovieRepository();
        Context.movieRepository = inMemoryMovieRepository;
    }

    @Test
    public void afterCreatingMovie_GatewayShouldTheEntry() {
        String id1 = createMovie(MovieType.REGULAR, "Movie Name 1");
        String id2 = createMovie(MovieType.CHILDRENS, "Movie Name 2");

        MovieData movie = inMemoryMovieRepository.findById(id1);
        assertEquals(id1, movie.id);
        assertEquals("Movie Name 1", movie.name);
        assertEquals(MovieType.REGULAR, movie.type);

        movie = inMemoryMovieRepository.findById(id2);
        assertEquals(id2, movie.id);
        assertEquals("Movie Name 2", movie.name);
        assertEquals(MovieType.CHILDRENS, movie.type);
    }

    @Test
    public void afterSavingAMovie_RepositoryShouldNotReflectTransientChanges() {
        MovieData movie = new MovieData();
        movie.id = "1";
        movie.name = "a";

        inMemoryMovieRepository.save(movie);

        movie.name = "b";

        assertNotEquals(movie.name, inMemoryMovieRepository.findById(movie.id).name);
    }

    private String createMovie(MovieType type, String name) {
        return new CreateMovieUseCase(type, name).execute();
    }
}
