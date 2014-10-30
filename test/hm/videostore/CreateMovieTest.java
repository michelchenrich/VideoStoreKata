package hm.videostore;

import hm.videostore.repository.MovieData;
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
        String id1 = createMovie(Type.REGULAR, "Movie Name 1");
        String id2 = createMovie(Type.CHILDRENS, "Movie Name 2");

        MovieData movie = inMemoryMovieRepository.findById(id1);
        assertEquals(id1, movie.id);
        assertEquals("Movie Name 1", movie.name);
        assertEquals(Type.REGULAR.code, movie.typeCode);

        movie = inMemoryMovieRepository.findById(id2);
        assertEquals(id2, movie.id);
        assertEquals("Movie Name 2", movie.name);
        assertEquals(Type.CHILDRENS.code, movie.typeCode);
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

    private String createMovie(Type type, String name) {
        return new CreateMovieUseCase(type.code, name).execute();
    }
}
