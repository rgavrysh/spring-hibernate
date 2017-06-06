package hib.dao;

import hib.model.Booking;
import hib.model.Customer;
import hib.model.Venue;

import java.util.List;

public interface BookingDao {
    Booking find(final Booking booking);

    List<Booking> findAllByVenue(Venue venue);

    List<Booking> findAllByVenueAndCustomer(final int venueId, final int customerId);

    Booking findOneById(final int bookingId);

    Booking create(final Booking booking);

    List<Booking> findAllByTimeRange(final Booking booking);

    void delete(final int bookingId);

    List<Booking> findAllByCustomer(Customer customer);
}
