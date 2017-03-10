package hib.bo;

import hib.dao.CustomerDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Customer;
import hib.model.Role;
import hib.model.UserToRole;
import hib.restEntity.CreateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Customer user = customerDao.findByLogin(login);
        if(user == null)
            throw new UsernameNotFoundException(String.format("User %s does not exist", login));
        return new CustomerDetails(user);
    }

    private final static class CustomerDetails extends Customer implements UserDetails {

        private CustomerDetails(Customer customer){
            super(customer);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<Role> roles = new HashSet<>();
            for (UserToRole role : getUserToRoles()) {
                roles.add(role.getRoleId());
            }
            return roles;
        }

        @Override
        public String getPassword() {
            return "****";
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
