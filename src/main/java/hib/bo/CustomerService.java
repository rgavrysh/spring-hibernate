package hib.bo;

import hib.model.Customer;
import hib.restEntity.CreateCustomer;

public interface CustomerService {
    Customer findOneById(final int id);
    Customer create(final CreateCustomer createCustomer);
}
