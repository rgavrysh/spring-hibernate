package hib.bo;

import hib.model.Booking;
import hib.model.Venue;

import java.util.List;

public interface BookingService {
    List<Booking> findAllByVenue(Venue venue);
}
