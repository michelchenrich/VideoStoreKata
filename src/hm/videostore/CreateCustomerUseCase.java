package hm.videostore;

import static hm.videostore.Context.customerRepository;
import hm.videostore.data.CustomerData;

public class CreateCustomerUseCase {
    private String name;

    public CreateCustomerUseCase(String name) {
        this.name = name;
    }

    public String execute() {
        CustomerData customer = new CustomerData();
        customer.id = customerRepository.getNextId();
        customer.name = name;
        customerRepository.save(customer);
        return customer.id;
    }
}
