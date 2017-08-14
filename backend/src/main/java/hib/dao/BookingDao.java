package hib.dao;

import hib.model.Booking;
import hib.model.Customer;
import hib.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BookingDao extends CrudRepository<Booking, Integer> {
    List<Booking> findAllByVenue(Venue venue);

    List<Booking> findAllByVenueAndCustomer(final Venue venue, final Customer customer);

    @Query(value = "select b from booking b where b.venue=:venueId" +
            " and (b.endDateTime>:startDate and b.startDateTime<:endDate)")
    List<Booking> findAllByTimeRange(@Param("venueId") Venue venue,
                                     @Param("startDate") Date startDateTime,
                                     @Param("endDate") Date endDateTime);

    List<Booking> findAllByCustomer(Customer customer);
}
