package hm.videostore;

import hm.videostore.repository.CustomerData;

class InMemoryCustomerRepository extends InMemoryRepository<CustomerData> {
    public CustomerData makeCopy(CustomerData customer) {
        CustomerData copy = new CustomerData();
        copy.id = customer.id;
        copy.name = customer.name;
        return copy;
    }
}