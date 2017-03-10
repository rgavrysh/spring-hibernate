package hib.dao;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    @Override
    public Customer findByLogin(String login) {
        Query query = entityManager.createNativeQuery("select * from customer where name=?", Customer.class);
        query.setParameter(1, login);
        Customer customer = (Customer) query.getSingleResult();
        return customer;
    }

    @Override
    public Customer create(final Customer customer) {
        logger.debug("DAO: add new customer");
        entityManager.persist(customer);
        entityManager.flush();
        return customer;
    }

    @Override
    public Customer find(Customer customer) {
        logger.debug("DAO: find customer");
        Query query = entityManager.createNativeQuery("select * from customer c where c.name=? and c.phone=? " +
                "and c.email=?", Customer.class);
        query.setParameter(1, customer.getName());
        query.setParameter(2, customer.getPhone());
        query.setParameter(3, customer.getEmail());
        Customer cstm = (Customer) query.getSingleResult();
        return cstm;
    }
}
