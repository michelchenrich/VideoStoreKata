package hm.videostore;

import hm.videostore.customer.CreateCustomerRequest;
import hm.videostore.customer.CreateCustomerUseCase;
import hm.videostore.customer.ReadCustomersUseCase;
import hm.videostore.data.Context;
import hm.videostore.data.Customer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class CreateCustomerTest {
    private Collection<Customer> customers;

    @Before
    public void setUp() {
        Context.customerRepository = new InMemoryRepository<Customer>();
    }

    @Test
    public void afterCreatingMovie_GatewayShouldTheEntry() {
        String id1 = createCustomer("Customer Name 1");
        String id2 = createCustomer("Customer Name 2");

        readCustomers();

        assertCustomerExists(id1, "Customer Name 1");
        assertCustomerExists(id2, "Customer Name 2");
    }

    private void assertCustomerExists(String id, String name) {
        for (Customer customer : customers) {
            if (customer.id.equals(id)) {
                assertEquals(id, customer.id);
                assertEquals(name, customer.name);
                return;
            }
        }
        fail();
    }

    private void readCustomers() {
        customers = new ReadCustomersUseCase().execute().customers;
    }

    private String createCustomer(String name) {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.name = name;
        return new CreateCustomerUseCase().execute(request).id;
    }
}
