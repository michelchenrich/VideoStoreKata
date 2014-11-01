package hm.videostore.customer;

import static hm.videostore.data.Context.customerRepository;
import hm.videostore.data.Customer;

public class CreateCustomerUseCase {
    public CreateCustomerResponse execute(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.id = customerRepository.getNextId();
        customer.name = request.name;
        customerRepository.save(customer);

        CreateCustomerResponse response = new CreateCustomerResponse();
        response.id = customer.id;
        return response;
    }
}
