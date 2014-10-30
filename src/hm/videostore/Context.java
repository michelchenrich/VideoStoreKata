package hm.videostore;

import hm.videostore.data.CustomerData;
import hm.videostore.data.MovieData;
import hm.videostore.data.Repository;

public class Context {
    public static Repository<MovieData> movieRepository;
    public static Repository<CustomerData> customerRepository;
}
