package hib.dao;

import hib.model.Venue;
import org.springframework.data.repository.CrudRepository;

public interface VenueDao extends CrudRepository<Venue, Integer> {
    Venue findByName(String name);
}
