package hib.dao;

import hib.model.Customer;

public interface CustomerDao {
    Customer findOneById(final int id);
}
