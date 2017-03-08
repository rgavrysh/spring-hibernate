package hib.dao;

import hib.model.Customer;

public interface CustomerDao {
    Customer findOneById(final int id);
    Customer create(final Customer customer);
    Customer find(final Customer customer);
}
