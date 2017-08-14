package hib.services;

import hib.dao.BookingDao;
import hib.dao.CustomerDao;
import hib.dao.VenueDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Booking;
import hib.model.Customer;
import hib.model.Venue;
import hib.restEntity.BookTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service("bookingService")
@Repository
public class BookingServiceImpl implements BookingService {
    private final APILogger<BookingServiceImpl> logger = new APILoggerImpl<>(this);

    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private VenueService venueService;
    @Autowired
    private CustomerService customerService;

    @Override
    public List<Booking> findAllByVenue(Venue venue) {
        logger.info("Find all bookings by venue.");
        return bookingDao.findAllByVenue(venue);
    }

    @Override
    public List<Booking> findAllByVenueAndCustomer(int venueId, Customer customerId) {
        logger.info("Find all bookings by venue: " + venueId + " and customer: " + customerId);
        return bookingDao.findAllByVenueAndCustomer(venueService.findOneById(venueId), customerId);
    }

    @Override
    public List<Booking> findAllByCustomer(Customer customer) {
        return bookingDao.findAllByCustomer(customer);
    }

    @Override
    public Booking create(final BookTime bookTime, final int venueId) {
        logger.info("Book new time for venue by id: " + venueId);
        Venue venue = venueService.findOneById(venueId);
        Customer customer = customerService.findOneById(bookTime.getCustomerId());
        Booking booking = new Booking(venue, customer, bookTime.getStartDateTime(), bookTime.getEndDateTime());
        if (isTimeSlotAvailable(booking)) {
            return bookingDao.save(booking);
        } else {
            logger.info("Time slot is not available");
            throw new NoResultException("Time slot is not available.");
        }
    }

    @Override
    public void delete(final int id) {
        logger.info("Service: remove booking");
        bookingDao.delete(id);
    }

    private boolean isTimeSlotAvailable(Booking booking) {
        logger.info("Find booking for specified time range. Start date:" + booking.getStartDateTime() +
                ",\tEnd Date: " + booking.getEndDateTime());
        List<Booking> bookings = bookingDao.findAllByTimeRange(booking.getVenue(), booking.getStartDateTime(),
                booking.getEndDateTime());
        logger.info("Found: " + booking.toString());
        return bookings.isEmpty();
    }
}
