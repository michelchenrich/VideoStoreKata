package hm.videostore;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class CreateMovieTest {
    private InMemoryMovieGateway fakeGateway;
    private String lastCreatedId;

    @Before
    public void setUp() {
        fakeGateway = new InMemoryMovieGateway();
        Context.gateway = fakeGateway;
    }

    @Test
    public void afterCreatingMovie_GatewayShouldTheEntry() {
        whenCreatingMovie(Type.REGULAR, "Movie Name");
        assertEquals(1, fakeGateway.movies.size());
        assertEquals("Movie Name", fakeGateway.movies.get(lastCreatedId).getName());
        assertEquals(Type.REGULAR.code, fakeGateway.movies.get(lastCreatedId).getTypeCode());
    }

    private void whenCreatingMovie(Type type, String name) {
        lastCreatedId = new CreateMovieUseCase(type.code, name).execute();
    }
}
