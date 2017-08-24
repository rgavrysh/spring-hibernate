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

    @GetMapping("/bookings/venue")
    @ResponseBody
    public List<Booking> getAllBookingsOfVenueByName(@RequestParam(value = "name") String venueName) {
        logger.info("Get all bookings for venue: " + venueName);
        Venue venue = venueService.findOneByName(venueName);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }

    @GetMapping("/me/bookings/venue/{venueId}")
    @ResponseBody
    public List<Booking> getBookingsOfVenueByUser(@AuthenticationPrincipal Customer customer, @PathVariable final int venueId) {
        logger.info("Get booking of venue requested by user " + customer.getName());
        List<Booking> bookings = bookingService.findAllByVenueAndCustomer(venueId, customer);
        return bookings;
    }

    //todo: change value to "/me/bookings"
    @GetMapping("/me/bookings/venue")
    @ResponseBody
    public List<Booking> getBookingsByUser(@AuthenticationPrincipal Customer customer) {
        logger.info("Get all bookings by user " + customer.getName());
        return bookingService.findAllByCustomer(customer);
    }

    @GetMapping("/bookings/venue/{id}")
    @ResponseBody
    public List<Booking> getAllBookingsOfVenueById(@PathVariable final int id) {
//        todo: allow only for users with admin role
        logger.info("Get all bookings for venue by id: " + id);
        Venue venue = venueService.findOneById(id);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }

    @PostMapping(value = "/bookings/venue/{venueId}/bookTime", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Booking bookTimeOnVenue(@AuthenticationPrincipal final Customer customer,
                            @PathVariable final int venueId,
                            @RequestBody BookTime bookTime) {
        logger.info("Book time for venue with id: " + venueId + "; start time: " + bookTime.getStartDateTime()
                + ", end time: " + bookTime.getEndDateTime());
        bookTime.setCustomerId(customer.getId().intValue());
        Booking booking = bookingService.create(bookTime, venueId);
        return booking;
    }

    @DeleteMapping("/bookings/{id}")
    @ResponseBody
    public Response deleteBookingById(@PathVariable final int id) {
        logger.info("Controller: removing booking by id: " + id);
        bookingService.delete(id);
        return new Response("Entity deleted", String.valueOf(HttpStatus.OK),
                "Booking has been removed");
    }
}
