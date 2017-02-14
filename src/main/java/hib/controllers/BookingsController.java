package hib.controllers;

import hib.bo.BookingService;
import hib.bo.VenueService;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Booking;
import hib.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody
    List<Booking> getAllBookingsOfVenueByName(@RequestParam(value = "name") String venueName){
        logger.info("Get all bookings for venue: " + venueName);
        Venue venue = venueService.findOneByName(venueName);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }

    @RequestMapping(value = "/bookings/venue/id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Booking> getAllBookingsOfVenueById(@RequestParam(value = "id") final int id){
        logger.info("Get all bookings for venue by id: " + id);
        Venue venue = venueService.findOneById(id);
        List<Booking> bookings = bookingService.findAllByVenue(venue);
        return bookings;
    }
}
