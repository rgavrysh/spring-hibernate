package hib.controllers;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Customer;
import hib.restEntity.CreateCustomer;
import hib.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final APILogger<CustomerController> logger = new APILoggerImpl<>(this);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Customer getMe(@AuthenticationPrincipal Customer me) {
        logger.info(me.toString());
        return me;
    }

    //    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Customer> getCustomers() {
        logger.info("Get all customers.");
        return customerService.listAllUsers();
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Customer getCustomer(@PathVariable final int id) {
        logger.info("Get customer by id: " + id);
        return customerService.findOneById(id);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Customer addNewCustomer(@RequestBody CreateCustomer createCustomer) {
        logger.info("Add new customer");
        return customerService.create(createCustomer);
    }

    @RequestMapping(value = "customer/{id}/delete", method = RequestMethod.DELETE)
    public
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    void deleteUser(@PathVariable final int id) {
        customerService.delete(id);
    }
}
