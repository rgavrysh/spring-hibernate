package hib.dao;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {
    private final APILogger<CustomerDaoImpl> logger = new APILoggerImpl<>(this);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer findOneById(int id) {
        logger.debug("Finding customer by id: " + id);
        return entityManager.find(Customer.class, id);
    }
}
