package hib.dao;

import hib.model.Customer;

import java.util.List;

public interface CustomerDao {
    Customer findOneById(final int id);

    Customer findByLogin(final String login);

    Customer create(final Customer customer);

    Customer find(final Customer customer);

    List<Customer> listUsers();

    void delete(Customer customer);
}
