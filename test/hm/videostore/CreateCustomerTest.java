package hm.videostore;

import hm.videostore.customer.CreateCustomerRequest;
import hm.videostore.customer.CreateCustomerUseCase;
import hm.videostore.data.Context;
import hm.videostore.data.Customer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

public class CreateCustomerTest {
    private InMemoryCustomerRepository inMemoryCustomerRepository;

    @Before
    public void setUp() {
        inMemoryCustomerRepository = new InMemoryCustomerRepository();
        Context.customerRepository = inMemoryCustomerRepository;
    }

    @Test
    public void afterCreatingMovie_GatewayShouldTheEntry() {
        String id1 = createCustomer("Customer Name 1");
        String id2 = createCustomer("Customer Name 2");

        Customer customer = inMemoryCustomerRepository.findById(id1);
        assertEquals(id1, customer.id);
        assertEquals("Customer Name 1", customer.name);

        customer = inMemoryCustomerRepository.findById(id2);
        assertEquals(id2, customer.id);
        assertEquals("Customer Name 2", customer.name);
    }

    @Test
    public void afterSavingAMovie_RepositoryShouldNotReflectTransientChanges() {
        Customer customer = new Customer();
        customer.id = "1";
        customer.name = "a";

        inMemoryCustomerRepository.save(customer);

        customer.name = "b";

        assertNotEquals(customer.name, inMemoryCustomerRepository.findById(customer.id).name);
    }

    private String createCustomer(String name) {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.name = name;
        return new CreateCustomerUseCase().execute(request).id;
    }
}
