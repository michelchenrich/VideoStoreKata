package hm.videostore;

import hm.videostore.repository.MovieData;

public class MovieFactory {
    public static Movie make(MovieData data) {
        Movie movie;
        if (data.typeCode == 1) movie = new RegularMovie();
        else if (data.typeCode == 2) movie = new ChildrensMovie();
        else movie = new NewReleaseMovie();
        return movie;
    }
}
