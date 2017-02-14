package hib.bo;

import hib.model.Venue;

public interface VenueService {
    Venue findOneByName(String name);
    Venue findOneById(int id);
}
