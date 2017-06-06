package hib.dao;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("customerDao")
@Transactional
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
        //todo: use save() instead of persist()
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

    @Override
    public List<Customer> listUsers() {
//        return entityManager.createQuery("from customer", Customer.class).getResultList();
        return entityManager.createNativeQuery("select * from customer;", Customer.class).getResultList();
    }

    @Override
    public void delete(Customer customer) {
        entityManager.merge(customer);
        entityManager.remove(customer);
    }
}
