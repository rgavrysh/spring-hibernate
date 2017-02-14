package hib.dao;

import hib.model.Booking;
import hib.model.Venue;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository("bookingDao")
public class BookingDaoImpl implements BookingDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Booking> findAllByVenue(Venue venue) {
        List<Booking> bookings = new ArrayList<>();
        Query query = entityManager.createNativeQuery("select * from booking b where b.venue_id=?", Booking.class);
        query.setParameter(1, venue.getId());
        bookings = query.getResultList();
        return bookings;
    }
}
