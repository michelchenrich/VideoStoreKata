package hm.videostore;

import hm.videostore.data.Customer;

class InMemoryCustomerRepository extends InMemoryRepository<Customer> {
    public Customer makeCopy(Customer customer) {
        Customer copy = new Customer();
        copy.id = customer.id;
        copy.name = customer.name;
        copy.frequentRenterPoints = customer.frequentRenterPoints;
        return copy;
    }
}