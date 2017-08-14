package hib.services;

import hib.dao.CustomerDao;
import hib.dao.RoleDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Customer;
import hib.model.Role;
import hib.restEntity.CreateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Repository
public class CustomerServiceImpl implements CustomerService {

    private final APILogger<CustomerServiceImpl> logger = new APILoggerImpl<>(this);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private RoleDao roleDao;

    @Value("${default.user.password}")
    private String defaultPassword;

    @Override
    @Transactional(readOnly = true)
    public Customer findOneById(int id) {
        logger.debug("Service: Find customer by id: " + id);
        return customerDao.findOne(id);
    }

    @Override
    @Transactional
    public Customer create(final CreateCustomer createCustomer) {
        Customer customer = new Customer(createCustomer);
        customer.setPassword(passwordEncoder.encode(defaultPassword));
        Set<Role> roles = new HashSet<>();
        Role role = roleDao.findOneById(2);
        roles.add(role);
        customer.setRole(roles);
        logger.info("Service: Create new customer: " + customer.toString());
        return customerDao.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listAllUsers() {
        logger.info("Service: Get all customers.");
        return (List<Customer>) customerDao.findAll();
    }

    @Override
    @Transactional
    public void delete(final int id) {
        logger.info("Service: Delete customer by id: " + id);
        customerDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Customer user = customerDao.findByName(login);
        if (user == null)
            throw new UsernameNotFoundException(String.format("User %s does not exist", login));
        return new CustomerDetails(user);
    }

    private final static class CustomerDetails extends Customer implements UserDetails {

        private CustomerDetails(Customer customer) {
            super(customer);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<Role> roles = getRole();
            return roles;
        }

        @Override
        public String getUsername() {
            return getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
