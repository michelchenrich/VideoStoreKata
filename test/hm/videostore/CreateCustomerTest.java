package hm.videostore;

import hm.videostore.repository.CustomerData;
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

        CustomerData customer = inMemoryCustomerRepository.findById(id1);
        assertEquals(id1, customer.id);
        assertEquals("Customer Name 1", customer.name);

        customer = inMemoryCustomerRepository.findById(id2);
        assertEquals(id2, customer.id);
        assertEquals("Customer Name 2", customer.name);
    }

    @Test
    public void afterSavingAMovie_RepositoryShouldNotReflectTransientChanges() {
        CustomerData customer = new CustomerData();
        customer.id = "1";
        customer.name = "a";

        inMemoryCustomerRepository.save(customer);

        customer.name = "b";

        assertNotEquals(customer.name, inMemoryCustomerRepository.findById(customer.id).name);
    }

    private String createCustomer(String name) {
        return new CreateCustomerUseCase(name).execute();
    }
}
