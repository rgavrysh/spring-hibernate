package hib.bo;

import hib.dao.CustomerDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Booking;
import hib.model.Customer;
import hib.model.Role;
import hib.restEntity.CreateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {
    private final APILogger<CustomerServiceImpl> logger = new APILoggerImpl<>(this);
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BookingService bookingService;

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
    public List<Customer> listAllUsers() {
        return customerDao.listUsers();
    }

    @Override
    public void delete(final int id) {
        //todo: implement rollback logic or cascade deleting
        Customer customer = customerDao.findOneById(id);
        List<Booking> bookings = bookingService.findAllByCustomer(customer);
        for (Booking booking : bookings){
            bookingService.delete(booking.getId());
        }
        customerDao.delete(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Customer user = customerDao.findByLogin(login);
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
