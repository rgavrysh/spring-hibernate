package hib.bo;

import hib.dao.BookingDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Booking;
import hib.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    private final APILogger<BookingServiceImpl> logger = new APILoggerImpl<>(this);

    @Override
    public List<Booking> findAllByVenue(Venue venue) {
        logger.info("Find all bookings by venue.");
        return bookingDao.findAllByVenue(venue);
    }
}
