package com.home.smoke;

import hib.Starter;
import hib.controllers.BookingsController;
import hib.controllers.CustomerController;
import hib.controllers.VenueController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Starter.class)
public class SpringSmokeTest {

    @Autowired
    private BookingsController bookingsController;

    @Autowired
    private VenueController venueController;

    @Autowired
    private CustomerController customerController;

    @Test
    public void bookingControllerLoad() {
        assertThat(bookingsController).isNotNull();
    }

    @Test
    public void venueControllerLoad() {
        assertThat(venueController).isNotNull();
    }

    @Test
    public void customerControllerLoad() {
        assertThat(customerController).isNotNull();
    }
}
