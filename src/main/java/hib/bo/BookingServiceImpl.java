package hib.bo;

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
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

    @Autowired
    private VenueDao venueDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BookingDao bookingDao;

    private final APILogger<BookingServiceImpl> logger = new APILoggerImpl<>(this);

    @Override
    public List<Booking> findAllByVenue(Venue venue) {
        logger.info("Find all bookings by venue.");
        return bookingDao.findAllByVenue(venue);
    }

    @Override
    public Booking create(final BookTime bookTime, final int venueId) {
        logger.info("Book new time for venue by id: " + venueId);
        Venue venue = venueDao.findOneById(venueId);
        Customer customer = customerDao.findOneById(bookTime.getCustomerId());
        Booking booking = new Booking(venue, customer, bookTime.getStartDateTime(), bookTime.getEndDateTime());
        if (isTimeSlotAvailable(booking)) {
            bookingDao.create(booking);
            return bookingDao.find(booking);
        } else {
            throw new NoResultException("Time slot is not available.");
        }
    }

    private boolean isTimeSlotAvailable(Booking booking) {
        logger.info("Find booking for specified time range.");
        List<Booking> bookings = bookingDao.findAllByTimeRange(booking);
        return bookings.isEmpty();
    }
}
