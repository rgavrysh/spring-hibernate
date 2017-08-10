package hib.services;

import hib.model.Booking;
import hib.model.Customer;
import hib.model.Venue;
import hib.restEntity.BookTime;

import java.util.List;

public interface BookingService {
    List<Booking> findAllByVenue(Venue venue);

    List<Booking> findAllByVenueAndCustomer(final int venueId, final int customerId);

    Booking create(final BookTime bookTime, final int venueId);

    void delete(final int id);

    Booking findById(final int id);

    List<Booking> findAllByCustomer(Customer customer);
}
