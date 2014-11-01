package hm.videostore.customer;

import static hm.videostore.data.Context.customerRepository;

public class ReadCustomersUseCase {
    public ReadCustomersResponse execute() {
        ReadCustomersResponse response = new ReadCustomersResponse();
        response.customers = customerRepository.findAll();
        return response;
    }
}