package hib.dao;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Venue;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("venueDao")
//@Transactional
public class VenueDaoImpl implements VenueDao {
    private final APILogger<VenueDaoImpl> logger = new APILoggerImpl<>(this);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Venue findOneByName(String name) {
        logger.debug("Find venue by name: " + name);
        Query query = entityManager.createNativeQuery("select * from venue v where v.name=?", Venue.class);
        query.setParameter(1, name);
        Venue venue = (Venue) query.getSingleResult();
        return venue;
    }

    @Override
    public Venue findOneById(Integer id) {
        logger.debug("Find venue by id: " + id);
        Query query = entityManager.createNativeQuery("select * from venue v where v.id=?", Venue.class);
        query.setParameter(1, id);
        Venue venue = null;
        try {
            venue = (Venue) query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            throw new NoResultException("Venue with id: " + id + " does not exist in DB");
        }
        return venue;
    }

    @Override
    public Venue create(Venue venue) {
        entityManager.persist(venue);
        entityManager.flush();
        return venue;
    }

    @Override
    public Venue find(Venue venue) {
        Query query = entityManager.createNativeQuery("select * from venue v where v.name=? and v.phone=? " +
                "and v.address_id=?", Venue.class);
        query.setParameter(1, venue.getName());
        query.setParameter(2, venue.getPhone());
        query.setParameter(3, venue.getAddress().getId());
        Venue persistedVenue = (Venue) query.getSingleResult();
        return persistedVenue;
    }

    @Override
    public void delete(final Venue venue) {
        logger.debug("DAO: delete venue with id: " + venue.getId());
        entityManager.remove(entityManager.contains(venue) ? venue : entityManager.merge(venue));
        entityManager.flush();
    }
}
