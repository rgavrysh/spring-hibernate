package hib.dao;

import hib.model.Booking;
import hib.model.Venue;

import java.util.List;

public interface BookingDao {
    List<Booking> findAllByVenue(Venue venue);
}
