package hib.dao;

import hib.model.Venue;

public interface VenueDao {
    Venue findOneByName(String name);

    Venue findOneById(Integer id);

    Venue create(final Venue venue);

    Venue find(final Venue venue);

    void delete(final Venue venue);
}
