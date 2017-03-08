package hib.controllers;

import hib.bo.BookingService;
import hib.bo.VenueService;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Booking;
import hib.model.Response;
import hib.model.Venue;
import hib.restEntity.BookTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingsController {
    private final APILogger<BookingsController> logger = new APILoggerImpl<>(this);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "/bookings/venue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<Booking> getAllBookingsOfVenueByName(@RequestParam(value = "name") String venueName) {
        logger.info("Get all bookings for venue: " + venueName);
        Venue venue = venueService.findOneByName(venueName);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }

    @RequestMapping(value = "/bookings/venue/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<Booking> getAllBookingsOfVenueById(@PathVariable final int id) {
        logger.info("Get all bookings for venue by id: " + id);
        Venue venue = venueService.findOneById(id);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }

    @RequestMapping(value = "/bookings/venue/{venueId}/bookTime", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Booking bookTimeOnVenue(@PathVariable final int venueId, @RequestBody(required = true) BookTime bookTime) {
        logger.info("Book time for venue with id: " + venueId + "; start time: " + bookTime.getStartDateTime()
                + ", end time: " + bookTime.getEndDateTime());
        Booking booking = bookingService.create(bookTime, venueId);
        return booking;
    }

    @RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Response deleteBookingById(@PathVariable final int id) {
        logger.info("Controller: removing booking by id: " + id);
        bookingService.delete(id);
        return new Response("Entity deleted", String.valueOf(HttpStatus.OK),
                "Booking has been removed");
    }
}
