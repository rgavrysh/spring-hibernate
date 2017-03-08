package hib.dao;

import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("addressDao")
//@Transactional
public class AddressDaoImpl implements AddressDao {
    private final APILogger<AddressDaoImpl> logger = new APILoggerImpl<>(this);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Address create(Address address) {
        entityManager.persist(address);
        entityManager.flush();
        return address;
    }

    @Override
    public Address find(Address address) {
        Query query = entityManager.createNativeQuery("select * from address a where a.city=? and a.street=? " +
                "and a.building_number=? and a.postal_code=?", Address.class);
        query.setParameter(1, address.getCity());
        query.setParameter(2, address.getStreet());
        query.setParameter(3, address.getBuildingNumber());
        query.setParameter(4, address.getPostalCode());
        Address addr = (Address) query.getSingleResult();
        return addr;
    }
}
