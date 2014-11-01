package hm.videostore;

import hm.videostore.data.Movie;
import hm.videostore.data.Repository;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class InMemoryRepositoryTest {
    private Repository<Movie> movieRepository;
    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movieRepository = new InMemoryRepository<Movie>();
        givenMovie("id", "Original");
    }

    private void givenMovie(String id, String name) {
        movie = new Movie();
        movie.id = id;
        movie.name = name;
        movieRepository.save(movie);
    }

    @Test
    public void afterSaving_noChangeShouldBeReflectedOnTheEntity() {
        movie.name = "Changed";

        Movie savedMovie = movieRepository.findById("id");
        assertEquals("Original", savedMovie.name);
    }

    @Test
    public void afterFinding_noChangeShouldBeReflectedOnTheEntityBeforeSaving() {
        movie = movieRepository.findById("id");
        movie.name = "Changed";

        Movie savedMovie = movieRepository.findById("id");
        assertEquals("Original", savedMovie.name);
    }

    @Test
    public void afterFindingAll_noChangeShouldBeReflectedOnTheEntitiesBeforeSaving() {
        givenMovie("id 2", "Original");

        Collection<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            movie.name = "Changed";
            Movie savedMovie = movieRepository.findById(movie.id);
            assertEquals("Original", savedMovie.name);
        }
    }
}
