package hib.controllers;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Booking;
import hib.model.Customer;
import hib.model.Venue;
import hib.restEntity.BookTime;
import hib.services.BookingService;
import hib.services.VenueService;
import hib.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingsController {
    private final APILogger<BookingsController> logger = new APILoggerImpl<>(this);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VenueService venueService;

    //    @RequestMapping(value = "/bookings/venue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/bookings/venue")
    public
    @ResponseBody
    List<Booking> getAllBookingsOfVenueByName(@RequestParam(value = "name") String venueName) {
        logger.info("Get all bookings for venue: " + venueName);
        Venue venue = venueService.findOneByName(venueName);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }

//    @RequestMapping(value = "/me/bookings/venue/{venueId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/me/bookings/venue/{venueId}")
    public
    @ResponseBody
    List<Booking> getBookingsOfVenueByUser(@AuthenticationPrincipal Customer customer, @PathVariable final int venueId) {
        logger.info("Get booking of venue requested by user " + customer.getName());
        List<Booking> bookings = bookingService.findAllByVenueAndCustomer(venueId, customer);
        return bookings;
    }

    //todo: change value to "/me/bookings"
//    @RequestMapping(value = "/me/bookings/venue", method = RequestMethod.GET)
    @GetMapping("/me/bookings/venue")
    public
    @ResponseBody
    List<Booking> getBookingsByUser(@AuthenticationPrincipal Customer customer) {
        logger.info("Get all bookings by user " + customer.getName());
        return bookingService.findAllByCustomer(customer);
    }

//    @RequestMapping(value = "/bookings/venue/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/bookings/venue/{id}")
    public
    @ResponseBody
    List<Booking> getAllBookingsOfVenueById(@PathVariable final int id) {
        logger.info("Get all bookings for venue by id: " + id);
        Venue venue = venueService.findOneById(id);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }

//    @RequestMapping(value = "/bookings/venue/{venueId}/bookTime", method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/bookings/venue/{venueId}/bookTime", consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Booking bookTimeOnVenue(@AuthenticationPrincipal final Customer customer,
                            @PathVariable final int venueId,
                            @RequestBody(required = true) BookTime bookTime) {
        logger.info("Book time for venue with id: " + venueId + "; start time: " + bookTime.getStartDateTime()
                + ", end time: " + bookTime.getEndDateTime());
        bookTime.setCustomerId(customer.getId().intValue());
        Booking booking = bookingService.create(bookTime, venueId);
        return booking;
    }

//    @RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping("/bookings/{id}")
    public
    @ResponseBody
    Response deleteBookingById(@PathVariable final int id) {
        logger.info("Controller: removing booking by id: " + id);
        bookingService.delete(id);
        return new Response("Entity deleted", String.valueOf(HttpStatus.OK),
                "Booking has been removed");
    }
}
