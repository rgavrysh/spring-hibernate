package hib.bo;

import hib.model.Booking;
import hib.model.Venue;
import hib.restEntity.BookTime;

import java.util.List;

public interface BookingService {
    List<Booking> findAllByVenue(Venue venue);

    Booking create(final BookTime bookTime, final int venueId);

    void delete(final int id);

    Booking findById(final int id);
}
