package hib.services;

import hib.dao.VenueDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Address;
import hib.model.Venue;
import hib.restEntity.CreateVenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service("venueService")
@Repository
public class VenueServiceImpl implements VenueService {

    private final APILogger<VenueServiceImpl> logger = new APILoggerImpl<>(this);

    @Autowired
    private VenueDao venueDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Venue findOneByName(final String name) {
        logger.info("Find venue by name: " + name);
        return venueDao.findByName(name);
    }

    @Override
    public Venue findOneById(final int id) {
        logger.info("Find venue by id: " + id);
        return venueDao.findOne(id);
    }

    @Override
    @Transactional
    public Venue create(CreateVenue createVenue) {
        Address address = new Address(createVenue.getCreateAddress());
        logger.info("Service: Persist address first: " + address.toString());
        entityManager.persist(address);
        Venue venue = new Venue(createVenue.getName(), createVenue.getPhone(), address, createVenue.getStartWork(),
                createVenue.getEndWork());
        logger.info("Service: Persist venue: " + venue.toString());
        entityManager.persist(venue);
        return venue;
    }

    @Override
    public void delete(final int id) {
        logger.info("Service: Delete venue with id: " + id);
        venueDao.delete(id);
    }

    @Override
    public List<Venue> listAllVenues() {
        logger.info("Service: List all Venues.");
        return (List<Venue>) venueDao.findAll();
    }
}
