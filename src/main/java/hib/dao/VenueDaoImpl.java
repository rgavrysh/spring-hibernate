package hib.dao;

import hib.model.Venue;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("venueDao")
public class VenueDaoImpl implements VenueDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Venue findOneByName(String name) {
        Query query = entityManager.createNativeQuery("select * from venue v where v.name=?", Venue.class);
        query.setParameter(1, name);
        Venue venue = (Venue) query.getSingleResult();
        return venue;
    }

    @Override
    public Venue findOneById(Integer id) {
        Query query = entityManager.createNativeQuery("select * from venue v where v.id=?", Venue.class);
        query.setParameter(1, id);
        Venue venue = (Venue) query.getSingleResult();
        return venue;
    }


}
