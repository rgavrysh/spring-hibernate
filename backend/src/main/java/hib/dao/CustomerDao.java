package hib.dao;

import hib.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDao extends CrudRepository<Customer, Integer> {
    Customer findByName(String login);
}
