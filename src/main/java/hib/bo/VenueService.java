package hib.bo;

import hib.model.Venue;
import hib.restEntity.CreateVenue;

import java.util.List;

public interface VenueService {
    Venue findOneByName(final String name);

    Venue findOneById(final int id);

    Venue create(final CreateVenue createVenue);

    void delete(final int id);

    List<Venue> listAllVenues();
}
