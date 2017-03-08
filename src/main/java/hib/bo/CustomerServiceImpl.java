package hib.bo;

import hib.dao.CustomerDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Customer;
import hib.restEntity.CreateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final APILogger<CustomerServiceImpl> logger = new APILoggerImpl<>(this);
    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer findOneById(int id) {
        logger.debug("Service: find customer by id: " + id);
        return customerDao.findOneById(id);
    }

    @Override
    public Customer create(final CreateCustomer createCustomer) {
        logger.info("Service: create new customer");
        Customer customer = new Customer(createCustomer);
        customerDao.create(customer);
        return customerDao.find(customer);
    }

}
