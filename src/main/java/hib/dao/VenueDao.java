package hib.dao;

import hib.model.Venue;

public interface VenueDao {
    Venue findOneByName(String name);

    Venue findOneById(Integer id);
}
