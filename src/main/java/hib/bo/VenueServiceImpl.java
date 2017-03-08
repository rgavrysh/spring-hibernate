package hib.bo;

import hib.dao.AddressDao;
import hib.dao.VenueDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Address;
import hib.model.Venue;
import hib.restEntity.CreateVenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service("venueService")
public class VenueServiceImpl implements VenueService {

    private final APILogger<VenueServiceImpl> logger = new APILoggerImpl<>(this);

    @Autowired
    private VenueDao venueDao;
    @Autowired
    private AddressDao addressDao;

    @Override
    public Venue findOneByName(final String name) {
        logger.info("Find venue by name: " + name);
        return venueDao.findOneByName(name);
    }

    @Override
    public Venue findOneById(final int id) {
        logger.info("Find venue by id: " + id);
        return venueDao.findOneById(id);
    }

    @Override
    public Venue create(CreateVenue createVenue) {
        logger.info("Service: add new venue.");
        Address address = new Address(createVenue.getCreateAddress());
        addressDao.create(address);
        Address persistedAddress = addressDao.find(address);
        logger.info("Address has been added to DB");
        Venue venue = new Venue(createVenue.getName(), createVenue.getPhone(), persistedAddress, createVenue.getStartWork(),
                createVenue.getEndWork());
        venueDao.create(venue);
        return venueDao.find(venue);
    }

    @Override
    public void delete(final int id) {
        logger.debug("Find venue with id: " + id + " before deleting");
        Venue venue = findOneById(id);
        if (venue == null) {
            throw new NoResultException("Venue with id: " + id + " does not exist in DB");
        }
        logger.info("Service: delete venue with id: " + id);
        venueDao.delete(venue);
    }
}
