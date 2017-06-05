package hib.bo;

import hib.model.Customer;
import hib.restEntity.CreateCustomer;

import java.util.List;

public interface CustomerService {
    Customer findOneById(final int id);
    Customer create(final CreateCustomer createCustomer);
    List<Customer> listAllUsers();
}
