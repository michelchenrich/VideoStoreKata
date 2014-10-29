package hm.videostore;

public class CreateMovieUseCase {
    private final int typeCode;
    private final String name;

    public CreateMovieUseCase(int typeCode, String name) {
        this.typeCode = typeCode;
        this.name = name;
    }

    public String execute() {
        Movie movie = Context.gateway.create();
        movie.setName(name);
        movie.setTypeCode(typeCode);
        Context.gateway.save(movie);
        return movie.getId();
    }
}
