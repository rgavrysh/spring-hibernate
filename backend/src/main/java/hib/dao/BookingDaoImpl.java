package hib.dao;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Booking;
import hib.model.Customer;
import hib.model.Venue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository("bookingDao")
@Transactional
public class BookingDaoImpl implements BookingDao {
    private final APILogger<BookingDaoImpl> logger = new APILoggerImpl<>(this);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Booking find(Booking booking) {
        logger.debug("Finding booking.");
        Query query = entityManager.createNativeQuery("select * from booking b where b.venue_id=? and " +
                "b.customer_id=? and b.start_date_time=? and b.end_date_time=?", Booking.class);
        query.setParameter(1, booking.getVenue().getId());
        query.setParameter(2, booking.getCustomer().getId());
        query.setParameter(3, booking.getStartDateTime());
        query.setParameter(4, booking.getEndDateTime());
        return (Booking) query.getSingleResult();
    }

    @Override
    public List<Booking> findAllByVenue(Venue venue) {
        logger.debug("Finding all bookings by venue.");
        List<Booking> bookings;
        Query query = entityManager.createNativeQuery("select * from booking b where b.venue_id=?", Booking.class);
        query.setParameter(1, venue.getId());
        bookings = query.getResultList();
        return bookings;
    }

    public List<Booking> findAllByVenueAndCustomer(int venueId, int customerId) {
        logger.debug("Find all bookings by venueId and customerId");
        List<Booking> bookings;
        Query query = entityManager.createNativeQuery("select * from booking b where b.venue_id=? and b.customer_id=?", Booking.class);
        query.setParameter(1, venueId);
        query.setParameter(2, customerId);
        bookings = query.getResultList();
        return bookings;
    }

    @Override
    public List<Booking> findAllByCustomer(Customer customer) {
        Query query = entityManager.createQuery("from booking where customer_id = :id", Booking.class);
        query.setParameter("id", customer.getId());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Booking findOneById(int bookingId) {
        logger.debug("Finding booking by id: " + bookingId);
        try {
            return entityManager.find(Booking.class, bookingId);
        } catch (NoResultException e) {
            throw new NoResultException("Entity not found");
        }
    }

    @Override
    public Booking create(Booking booking) {
        logger.debug("Creating entity: ");
        entityManager.persist(booking);
        entityManager.flush();
        return booking;
    }

    @Override
    public List<Booking> findAllByTimeRange(final Booking booking) {
        logger.debug("Find all bookings for venue id: " + booking.getVenue().getId() + ", and time range from: " +
                booking.getStartDateTime() + " to: " + booking.getEndDateTime());
        Query query = entityManager.createNativeQuery("select * from booking b where b.venue_id=?" +
                " and (b.end_date_time>? and start_date_time<?)");
        query.setParameter(1, booking.getVenue().getId());
        query.setParameter(2, booking.getStartDateTime());
        query.setParameter(3, booking.getEndDateTime());
        List<Booking> bookingList = query.getResultList();
        return bookingList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(final int bookingId) {
        logger.debug("DAO: remove booking");
        Booking booking = findOneById(bookingId);
        if (booking == null) {
            throw new NoResultException("Booking with id: " + bookingId + " does not exist.");
        }
        entityManager.remove(booking);
        entityManager.flush();
    }
}
