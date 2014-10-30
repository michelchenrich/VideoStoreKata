package hm.videostore;

import hm.videostore.repository.CustomerData;
import hm.videostore.repository.MovieData;
import hm.videostore.repository.Repository;

public class Context {
    public static Repository<MovieData> movieRepository;
    public static Repository<CustomerData> customerRepository;
}
