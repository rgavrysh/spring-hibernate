package hib.bo;

import hib.model.Customer;
import hib.restEntity.CreateCustomer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService extends UserDetailsService{
    Customer findOneById(final int id);
    Customer create(final CreateCustomer createCustomer);
    List<Customer> listAllUsers();
    void delete(final int id);
}
