package hm.videostore;

import hm.videostore.repository.MovieData;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class CreateMovieTest {
    private InMemoryRepository<MovieData> inMemoryRepository;

    @Before
    public void setUp() {
        inMemoryRepository = new InMemoryRepository<MovieData>();
        Context.movieRepository = inMemoryRepository;
    }

    @Test
    public void afterCreatingMovie_GatewayShouldTheEntry() {
        String id1 = createMovie(Type.REGULAR, "Movie Name 1");
        String id2 = createMovie(Type.CHILDRENS, "Movie Name 2");

        MovieData movie = inMemoryRepository.findById(id1);
        assertEquals(id1, movie.id);
        assertEquals("Movie Name 1", movie.name);
        assertEquals(Type.REGULAR.code, movie.typeCode);

        movie = inMemoryRepository.findById(id2);
        assertEquals(id2, movie.id);
        assertEquals("Movie Name 2", movie.name);
        assertEquals(Type.CHILDRENS.code, movie.typeCode);
    }

    private String createMovie(Type type, String name) {
        return new CreateMovieUseCase(type.code, name).execute();
    }
}
